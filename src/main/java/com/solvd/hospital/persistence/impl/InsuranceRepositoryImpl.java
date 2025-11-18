package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.Insurance;
import com.solvd.hospital.persistence.InsuranceRepository;
import com.solvd.hospital.persistence.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InsuranceRepositoryImpl implements InsuranceRepository {
    private static final String INSERT_SQL = "INSERT INTO insurances (insurance_type_id, policy_number, insured, expiry_date) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM insurances WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM insurances";
    private static final String SELECT_EXPIRING_SQL = "SELECT * FROM insurances WHERE expiry_date <= CURRENT_DATE";
    private static final String UPDATE_SQL = "UPDATE insurances SET insurance_type_id = ?, policy_number = ?, insured = ?, expiry_date = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM insurances WHERE id = ?";

    @Override
    public Insurance create(Insurance insurance) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, insurance.getInsuranceTypeId());
            statement.setString(2, insurance.getPolicyNumber());
            statement.setBoolean(3, insurance.isInsured());
            statement.setDate(4, toDate(insurance.getExpiryDate()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    insurance.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert insurance", e);
        }
        return insurance;
    }

    @Override
    public Optional<Insurance> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapInsurance(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find insurance by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Insurance> findAll() {
        List<Insurance> insurances = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                insurances.add(mapInsurance(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list insurances", e);
        }
        return insurances;
    }

    @Override
    public List<Insurance> findExpiringPolicies() {
        List<Insurance> insurances = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_EXPIRING_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                insurances.add(mapInsurance(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find expiring insurances", e);
        }
        return insurances;
    }

    @Override
    public Insurance update(Insurance insurance) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, insurance.getInsuranceTypeId());
            statement.setString(2, insurance.getPolicyNumber());
            statement.setBoolean(3, insurance.isInsured());
            statement.setDate(4, toDate(insurance.getExpiryDate()));
            statement.setLong(5, insurance.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update insurance", e);
        }
        return insurance;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete insurance", e);
        }
    }

    private Insurance mapInsurance(ResultSet resultSet) throws SQLException {
        Insurance insurance = new Insurance();
        insurance.setId(resultSet.getLong("id"));
        insurance.setInsuranceTypeId(resultSet.getLong("insurance_type_id"));
        insurance.setPolicyNumber(resultSet.getString("policy_number"));
        insurance.setInsured(resultSet.getBoolean("insured"));
        insurance.setExpiryDate(toLocalDate(resultSet.getDate("expiry_date")));
        return insurance;
    }

    private Date toDate(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }

    private LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
