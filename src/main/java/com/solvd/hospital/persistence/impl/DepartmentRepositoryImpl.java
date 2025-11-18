package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.Department;
import com.solvd.hospital.persistence.DepartmentRepository;
import com.solvd.hospital.persistence.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static final String INSERT_SQL = "INSERT INTO departments (hospital_id, code, name, description) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM departments WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM departments";
    private static final String SELECT_BY_HOSPITAL_SQL = "SELECT * FROM departments WHERE hospital_id = ?";
    private static final String UPDATE_SQL = "UPDATE departments SET hospital_id = ?, code = ?, name = ?, description = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM departments WHERE id = ?";

    @Override
    public Department create(Department department) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, department.getHospitalId());
            statement.setString(2, department.getCode());
            statement.setString(3, department.getName());
            statement.setString(4, department.getDescription());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert department", e);
        }
        return department;
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapDepartment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find department by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                departments.add(mapDepartment(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list departments", e);
        }
        return departments;
    }

    @Override
    public List<Department> findByHospitalId(Long hospitalId) {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_HOSPITAL_SQL)) {
            statement.setLong(1, hospitalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    departments.add(mapDepartment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find departments by hospital", e);
        }
        return departments;
    }

    @Override
    public Department update(Department department) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, department.getHospitalId());
            statement.setString(2, department.getCode());
            statement.setString(3, department.getName());
            statement.setString(4, department.getDescription());
            statement.setLong(5, department.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update department", e);
        }
        return department;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete department", e);
        }
    }

    private Department mapDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getLong("id"));
        department.setHospitalId(resultSet.getLong("hospital_id"));
        department.setCode(resultSet.getString("code"));
        department.setName(resultSet.getString("name"));
        department.setDescription(resultSet.getString("description"));
        return department;
    }
}
