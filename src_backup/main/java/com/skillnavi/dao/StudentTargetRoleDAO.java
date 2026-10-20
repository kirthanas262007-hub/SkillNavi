package com.skillnavi.dao;

import com.skillnavi.model.JobRole;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StudentTargetRoleDAO {
    public void saveSelectedRole(int studentId, int jobId) {
        String sql = """
                INSERT INTO STUDENT_TARGET_ROLE (student_id, job_id)
                VALUES (?, ?)
                ON DUPLICATE KEY UPDATE job_id = ?, selected_at = CURRENT_TIMESTAMP
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, jobId);
            statement.setInt(3, jobId);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to save the target job role.", exception);
        }
    }

    public Optional<JobRole> findSelectedRoleByStudentId(int studentId) {
        String sql = """
                SELECT jr.job_id, jr.job_title, jr.description
                FROM STUDENT_TARGET_ROLE str
                JOIN JOB_ROLE jr ON jr.job_id = str.job_id
                WHERE str.student_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(new JobRole(
                        resultSet.getInt("job_id"),
                        resultSet.getString("job_title"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to retrieve the selected target role.", exception);
        }
    }
}
