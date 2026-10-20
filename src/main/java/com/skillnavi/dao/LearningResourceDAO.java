
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
                SELECT lr.resource_id, lr.resource_name,
                       lr.resource_type, lr.resource_link,
                       s.skill_id, s.skill_name, s.category
                FROM learning_resources lr
                JOIN skills s ON s.skill_id = lr.skill_id
                WHERE lr.skill_id = ?
                ORDER BY lr.resource_name
                """;

        List<LearningResource> resources = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, skillId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Skill skill = new Skill(
                            rs.getInt("skill_id"),
                            rs.getString("skill_name"),
                            rs.getString("category")
                    );

                    resources.add(new LearningResource(
                            rs.getInt("resource_id"),
                            skill,
                            rs.getString("resource_name"),
                            rs.getString("resource_type"),
                            rs.getString("resource_link")
                    ));
                }
            }

            return resources;

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to retrieve learning resources.",
                    exception
            );
        }
    }
}
