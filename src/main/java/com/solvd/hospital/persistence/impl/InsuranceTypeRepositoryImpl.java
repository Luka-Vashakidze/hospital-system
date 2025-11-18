package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.InsuranceType;
import com.solvd.hospital.persistence.InsuranceTypeRepository;
import com.solvd.hospital.persistence.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InsuranceTypeRepositoryImpl implements InsuranceTypeRepository {
    private static final String INSERT_SQL = "INSERT INTO insurance_types (name, coverage_percentage, description) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM insurance_types WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM insurance_types";
    private static final String UPDATE_SQL = "UPDATE insurance_types SET name = ?, coverage_percentage = ?, description = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM insurance_types WHERE id = ?";

    @Override
    public InsuranceType create(InsuranceType insuranceType) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, insuranceType.getName());
            statement.setBigDecimal(2, insuranceType.getCoveragePercentage());
            statement.setString(3, insuranceType.getDescription());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    insuranceType.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert insurance type", e);
        }
        return insuranceType;
    }

    @Override
    public Optional<InsuranceType> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapInsuranceType(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find insurance type by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<InsuranceType> findAll() {
        List<InsuranceType> insuranceTypes = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                insuranceTypes.add(mapInsuranceType(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list insurance types", e);
        }
        return insuranceTypes;
    }

    @Override
    public InsuranceType update(InsuranceType insuranceType) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, insuranceType.getName());
            statement.setBigDecimal(2, insuranceType.getCoveragePercentage());
            statement.setString(3, insuranceType.getDescription());
            statement.setLong(4, insuranceType.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update insurance type", e);
        }
        return insuranceType;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete insurance type", e);
        }
    }

    private InsuranceType mapInsuranceType(ResultSet resultSet) throws SQLException {
        InsuranceType insuranceType = new InsuranceType();
        insuranceType.setId(resultSet.getLong("id"));
        insuranceType.setName(resultSet.getString("name"));
        insuranceType.setCoveragePercentage(resultSet.getBigDecimal("coverage_percentage"));
        insuranceType.setDescription(resultSet.getString("description"));
        return insuranceType;
    }
}
