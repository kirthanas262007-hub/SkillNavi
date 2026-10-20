package com.skillnavi.dao;

import com.skillnavi.model.Progress;
import com.skillnavi.model.Skill;
import com.skillnavi.model.enums.ProgressStatus;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;
import com.skillnavi.util.ValidationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgressDAO {
    public List<Progress> findByStudentId(int studentId) {
        String sql = """
                SELECT p.student_id, p.progress_percentage, p.status,
                       s.skill_id, s.skill_name, s.category
                FROM progress p
JOIN skills s ON s.skill_id = p.skill_id                WHERE p.student_id = ?
                ORDER BY s.skill_name
                """;
        List<Progress> progressEntries = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    progressEntries.add(mapProgress(resultSet));
                }
            }
            return progressEntries;
        } catch (SQLException exception) {
    exception.printStackTrace();

    throw new DatabaseException(
            "Unable to retrieve progress entries: "
                    + exception.getMessage(),
            exception
    );
}
    }

    public void saveOrUpdate(Progress progress) {
        validateProgress(progress);
        String sql = """
                INSERT INTO progress (student_id, skill_id, progress_percentage, status)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE progress_percentage = ?, status = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, progress.getStudentId());
            statement.setInt(2, progress.getSkill().getSkillId());
            statement.setInt(3, progress.getProgressPercentage());
            statement.setString(4, progress.getStatus().name());
            statement.setInt(5, progress.getProgressPercentage());
            statement.setString(6, progress.getStatus().name());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to save progress.", exception);
        }
    }

    private Progress mapProgress(ResultSet resultSet) throws SQLException {
        Skill skill = new Skill(
                resultSet.getInt("skill_id"),
                resultSet.getString("skill_name"),
                resultSet.getString("category")
        );
        return new Progress(
                resultSet.getInt("student_id"),
                skill,
                resultSet.getInt("progress_percentage"),
                ProgressStatus.fromDatabaseValue(resultSet.getString("status"))
        );
    }

    private void validateProgress(Progress progress) {
        if (progress == null || progress.getStudentId() <= 0
                || progress.getSkill() == null || progress.getSkill().getSkillId() == null
                || progress.getStatus() == null) {
            throw new IllegalArgumentException("Student, skill, percentage, and status are required.");
        }
        ValidationUtil.requireProgressPercentage(progress.getProgressPercentage());
    }
}
