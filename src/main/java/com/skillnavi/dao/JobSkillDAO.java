package com.skillnavi.dao;

import com.skillnavi.model.JobSkill;
import com.skillnavi.model.Skill;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.model.enums.SkillPriority;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobSkillDAO {
    public List<JobSkill> findByJobId(int jobId) {
        String sql = """
                SELECT js.job_id, js.required_level, js.priority,
                       s.skill_id, s.skill_name, s.category
                FROM JOB_SKILL js
                JOIN SKILL s ON s.skill_id = js.skill_id
                WHERE js.job_id = ?
                ORDER BY CASE js.priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 ELSE 3 END,
                         s.skill_name
                """;
        List<JobSkill> jobSkills = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, jobId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    jobSkills.add(mapJobSkill(resultSet));
                }
            }
            return jobSkills;
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to retrieve skills required for this job role.", exception);
        }
    }

    private JobSkill mapJobSkill(ResultSet resultSet) throws SQLException {
        Skill skill = new Skill(
                resultSet.getInt("skill_id"),
                resultSet.getString("skill_name"),
                resultSet.getString("category")
        );
        return new JobSkill(
                resultSet.getInt("job_id"),
                skill,
                ProficiencyLevel.fromDatabaseValue(resultSet.getString("required_level")),
                SkillPriority.fromDatabaseValue(resultSet.getString("priority"))
        );
    }
}
