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
    public List<Skill> findAll() {
        String sql = "SELECT skill_id, skill_name, category FROM SKILL ORDER BY skill_name";
        List<Skill> skills = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                skills.add(mapSkill(resultSet));
            }
            return skills;
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to retrieve skills.", exception);
        }
    }

    public Optional<Skill> findById(int skillId) {
        String sql = "SELECT skill_id, skill_name, category FROM SKILL WHERE skill_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, skillId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapSkill(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to retrieve the selected skill.", exception);
        }
    }

    private Skill mapSkill(ResultSet resultSet) throws SQLException {
        return new Skill(
                resultSet.getInt("skill_id"),
                resultSet.getString("skill_name"),
                resultSet.getString("category")
        );
    }
}
