
package com.skillnavi.dao;

import com.skillnavi.model.Skill;
import com.skillnavi.model.StudentSkill;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentSkillDAO {

    public List<StudentSkill> findByStudentId(int studentId) {
        String sql = """
                SELECT ss.student_id, ss.skill_level,
                       s.skill_id, s.skill_name, s.category
                FROM student_skills ss
                JOIN skills s ON s.skill_id = ss.skill_id
                WHERE ss.student_id = ?
                ORDER BY s.skill_name
                """;

        List<StudentSkill> studentSkills = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    studentSkills.add(mapStudentSkill(resultSet));
                }
            }

            return studentSkills;

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to retrieve the student's skills.", exception);
        }
    }

    public void saveOrUpdate(StudentSkill studentSkill) {
        validateStudentSkill(studentSkill);

        String sql = """
                INSERT INTO student_skills
                    (student_id, skill_id, skill_level)
                VALUES (?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    skill_level = VALUES(skill_level)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentSkill.getStudentId());
            statement.setInt(2, studentSkill.getSkill().getSkillId());
            statement.setInt(
                    3, studentSkill.getProficiencyLevel().getNumericValue());

            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to save the student's skill.", exception);
        }
    }

    public void delete(int studentId, int skillId) {
        String sql = """
                DELETE FROM student_skills
                WHERE student_id = ? AND skill_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setInt(2, skillId);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new DatabaseException(
                    "Unable to remove the student's skill.", exception);
        }
    }

    private StudentSkill mapStudentSkill(ResultSet resultSet)
            throws SQLException {

        Skill skill = new Skill(
                resultSet.getInt("skill_id"),
                resultSet.getString("skill_name"),
                resultSet.getString("category")
        );

        return new StudentSkill(
                resultSet.getInt("student_id"),
                skill,
                ProficiencyLevel.fromDatabaseValue(
                        String.valueOf(resultSet.getInt("skill_level")))
        );
    }

    private void validateStudentSkill(StudentSkill studentSkill) {
        if (studentSkill == null
                || studentSkill.getStudentId() <= 0
                || studentSkill.getSkill() == null
                || studentSkill.getSkill().getSkillId() == null
                || studentSkill.getProficiencyLevel() == null) {

            throw new IllegalArgumentException(
                    "Student, skill, and proficiency level are required.");
        }
    }
}

