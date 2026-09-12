package com.skillnavi.dao;

import com.skillnavi.model.Student;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;
import com.skillnavi.util.ValidationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class StudentDAO {
    public Student create(Student student) {
        validateStudent(student);
        String sql = "INSERT INTO STUDENT (name, email, education) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getEducation());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    student.setStudentId(keys.getInt(1));
                    return student;
                }
            }
            throw new DatabaseException("Student was created but no ID was returned.");
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to create the student profile.", exception);
        }
    }

    public void update(Student student) {
        if (student.getStudentId() == null) {
            throw new IllegalArgumentException("Student ID is required to update a profile.");
        }
        validateStudent(student);
        String sql = "UPDATE STUDENT SET name = ?, email = ?, education = ? WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getEducation());
            statement.setInt(4, student.getStudentId());

            if (statement.executeUpdate() == 0) {
                throw new DatabaseException("The student profile no longer exists.");
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to update the student profile.", exception);
        }
    }

    public Optional<Student> findByEmail(String email) {
        String sql = "SELECT student_id, name, email, education FROM STUDENT WHERE email = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ValidationUtil.requireValidEmail(email));
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapStudent(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to find the student profile.", exception);
        }
    }

    private Student mapStudent(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getInt("student_id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("education")
        );
    }

    private void validateStudent(Student student) {
        student.setName(ValidationUtil.requireText(student.getName(), "Name"));
        student.setEmail(ValidationUtil.requireValidEmail(student.getEmail()));
        student.setEducation(ValidationUtil.requireText(student.getEducation(), "Education"));
    }
}
