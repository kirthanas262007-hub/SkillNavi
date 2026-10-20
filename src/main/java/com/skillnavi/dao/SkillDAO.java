

package com.skillnavi.dao;

import com.skillnavi.model.Skill;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillDAO {

    // Retrieve all available skills from the database
    public List<Skill> findAll() {
        String sql =
                "SELECT skill_id, skill_name, category " +
                "FROM skills ORDER BY skill_name";

        List<Skill> skills = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                skills.add(mapSkill(resultSet));
            }

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to retrieve skills.", exception);
        }

        return skills;
    }

    // Retrieve one skill using its ID
    public Optional<Skill> findById(int skillId) {
        String sql =
                "SELECT skill_id, skill_name, category " +
                "FROM skills WHERE skill_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, skillId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapSkill(resultSet));
                }

                return Optional.empty();
            }

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to retrieve the selected skill.", exception);
        }
    }

    // Convert a database row into a Skill object
    private Skill mapSkill(ResultSet resultSet) throws SQLException {
        return new Skill(
                resultSet.getInt("skill_id"),
                resultSet.getString("skill_name"),
                resultSet.getString("category")
        );
    }
}
