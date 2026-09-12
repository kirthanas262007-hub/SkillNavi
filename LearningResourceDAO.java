package com.skillnavi.dao;

import com.skillnavi.model.LearningResource;
import com.skillnavi.model.Skill;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LearningResourceDAO {
    public List<LearningResource> findBySkillId(int skillId) {
        String sql = """
                SELECT lr.resource_id, lr.resource_name, lr.resource_type, lr.resource_link,
                       s.skill_id, s.skill_name, s.category
                FROM LEARNING_RESOURCE lr
                JOIN SKILL s ON s.skill_id = lr.skill_id
                WHERE lr.skill_id = ?
                ORDER BY lr.resource_name
                """;
        List<LearningResource> resources = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, skillId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    resources.add(mapResource(resultSet));
                }
            }
            return resources;
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to retrieve learning resources.", exception);
        }
    }

    private LearningResource mapResource(ResultSet resultSet) throws SQLException {
        Skill skill = new Skill(
                resultSet.getInt("skill_id"),
                resultSet.getString("skill_name"),
                resultSet.getString("category")
        );
        return new LearningResource(
                resultSet.getInt("resource_id"),
                skill,
                resultSet.getString("resource_name"),
                resultSet.getString("resource_type"),
                resultSet.getString("resource_link")
        );
    }
}
