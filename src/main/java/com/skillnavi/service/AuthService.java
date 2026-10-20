
package com.skillnavi.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;

import com.skillnavi.auth.PasswordUtil;
import com.skillnavi.dao.StudentDAO;
import com.skillnavi.model.Student;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;

public class AuthService {

    private final StudentDAO studentDAO = new StudentDAO();

    public Student register(
            String name,
            String email,
            String department,
            int year,
            String username,
            char[] password) {

        String cleanName = requireText(name, "Name");
        String cleanEmail = requireText(email, "Email")
                .toLowerCase(Locale.ROOT);
        String cleanDepartment = requireText(department, "Department");
        String cleanUsername = requireText(username, "Username");

        if (!cleanEmail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }

        if (!cleanUsername.matches("[A-Za-z0-9_.-]{3,50}")) {
            throw new IllegalArgumentException(
                    "Username must be 3–50 characters and use letters, numbers, dots, underscores or hyphens."
            );
        }

        if (year < 1 || year > 10) {
            throw new IllegalArgumentException("Enter a valid study year.");
        }

        if (password == null || password.length < 8) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters."
            );
        }

        char[] passwordCopy = Arrays.copyOf(password, password.length);

        try {
            String passwordHash = PasswordUtil.hashPassword(passwordCopy);

            try (Connection connection = DatabaseConnection.getConnection()) {
                connection.setAutoCommit(false);

                try {
                    // Check whether this email or username is already registered.
                    String checkSql =
                            "SELECT user_id FROM users WHERE username = ? OR email = ?";

                    try (PreparedStatement check = connection.prepareStatement(checkSql)) {
                        check.setString(1, cleanUsername);
                        check.setString(2, cleanEmail);

                        try (ResultSet result = check.executeQuery()) {
                            if (result.next()) {
                                throw new IllegalArgumentException(
                                        "That username or email is already registered."
                                );
                            }
                        }
                    }

                    // Create the student profile within the same transaction.
                    Student student = new Student(
                            cleanName, cleanEmail, cleanDepartment, year
                    );

                    String studentSql =
                            "INSERT INTO students (name, email, department, year) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement insertStudent = connection.prepareStatement(
                            studentSql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                        insertStudent.setString(1, student.getName());
                        insertStudent.setString(2, student.getEmail());
                        insertStudent.setString(3, student.getDepartment());
                        insertStudent.setInt(4, student.getYear());
                        insertStudent.executeUpdate();

                        try (ResultSet keys = insertStudent.getGeneratedKeys()) {
                            if (!keys.next()) {
                                throw new SQLException("Student ID was not generated.");
                            }
                            student.setStudentId(keys.getInt(1));
                        }
                    }

                    String userSql =
                            "INSERT INTO users (student_id, username, email, password_hash) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement insertUser = connection.prepareStatement(userSql)) {
                        insertUser.setInt(1, student.getStudentId());
                        insertUser.setString(2, cleanUsername);
                        insertUser.setString(3, cleanEmail);
                        insertUser.setString(4, passwordHash);
                        insertUser.executeUpdate();
                    }

                    connection.commit();
                    return student;

                } catch (Exception exception) {
                    try {
                        connection.rollback();
                    } catch (SQLException rollbackException) {
                        exception.addSuppressed(rollbackException);
                    }

                    if (exception instanceof IllegalArgumentException) {
                        throw (IllegalArgumentException) exception;
                    }

                    if (exception instanceof SQLException) {
                        SQLException sqlException = (SQLException) exception;

                        if ("23000".equals(sqlException.getSQLState())) {
                            throw new IllegalArgumentException(
                                    "That username or email is already registered.",
                                    sqlException
                            );
                        }
                    }

                    throw exception;

                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException ignored) {
                        // The connection is closing.
                    }
                }

            } catch (SQLException exception) {
                throw new DatabaseException(
                        "Unable to register the account.", exception
                );
            }

        } finally {
            Arrays.fill(passwordCopy, '\0');
        }
    }

    public Student login(String usernameOrEmail, char[] password) {
        String identifier = requireText(
                usernameOrEmail, "Username or email"
        );

        if (password == null || password.length == 0) {
            throw new IllegalArgumentException("Enter your password.");
        }

        String sql =
                "SELECT s.student_id, s.name, s.email, s.department, s.year, u.password_hash " +
                "FROM users u JOIN students s ON u.student_id = s.student_id " +
                "WHERE u.username = ? OR u.email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, identifier);
            statement.setString(2, identifier);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()
                        || !PasswordUtil.verifyPassword(
                                password, result.getString("password_hash"))) {
                    throw new IllegalArgumentException(
                            "Invalid username/email or password."
                    );
                }

                return new Student(
                        result.getInt("student_id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("department"),
                        result.getInt("year")
                );
            }

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to log in. Please check the database connection.",
                    exception
            );
        }
    }

    private String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return value.trim();
    }
}
