package com.skillnavi.dao;

import com.skillnavi.model.JobRole;
import com.skillnavi.util.DatabaseConnection;
import com.skillnavi.util.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobRoleDAO {
    public List<JobRole> findAll() {
        String sql = "SELECT job_id, job_title, description FROM JOB_ROLE ORDER BY job_title";
        List<JobRole> jobRoles = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                jobRoles.add(mapJobRole(resultSet));
            }
            return jobRoles;
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to retrieve job roles.", exception);
        }
    }

    public Optional<JobRole> findById(int jobId) {
        String sql = "SELECT job_id, job_title, description FROM JOB_ROLE WHERE job_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, jobId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapJobRole(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Unable to retrieve the selected job role.", exception);
        }
    }

    JobRole mapJobRole(ResultSet resultSet) throws SQLException {
        return new JobRole(
                resultSet.getInt("job_id"),
                resultSet.getString("job_title"),
                resultSet.getString("description")
        );
    }
}
