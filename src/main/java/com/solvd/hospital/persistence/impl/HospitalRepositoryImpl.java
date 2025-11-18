package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.persistence.HospitalRepository;
import com.solvd.hospital.persistence.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HospitalRepositoryImpl implements HospitalRepository {
    private static final String INSERT_SQL = "INSERT INTO hospitals (name, address, phone, email, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM hospitals WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM hospitals";
    private static final String UPDATE_SQL = "UPDATE hospitals SET name = ?, address = ?, phone = ?, email = ?, updated_at = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM hospitals WHERE id = ?";

    @Override
    public Hospital create(Hospital hospital) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, hospital.getName());
            statement.setString(2, hospital.getAddress());
            statement.setString(3, hospital.getPhone());
            statement.setString(4, hospital.getEmail());
            statement.setTimestamp(5, toTimestamp(hospital.getCreatedAt()));
            statement.setTimestamp(6, toTimestamp(hospital.getUpdatedAt()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hospital.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert hospital", e);
        }
        return hospital;
    }

    @Override
    public Optional<Hospital> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapHospital(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find hospital by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Hospital> findAll() {
        List<Hospital> hospitals = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                hospitals.add(mapHospital(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list hospitals", e);
        }
        return hospitals;
    }

    @Override
    public Hospital update(Hospital hospital) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, hospital.getName());
            statement.setString(2, hospital.getAddress());
            statement.setString(3, hospital.getPhone());
            statement.setString(4, hospital.getEmail());
            statement.setTimestamp(5, toTimestamp(hospital.getUpdatedAt()));
            statement.setLong(6, hospital.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update hospital", e);
        }
        return hospital;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete hospital", e);
        }
    }

    private Hospital mapHospital(ResultSet resultSet) throws SQLException {
        Hospital hospital = new Hospital();
        hospital.setId(resultSet.getLong("id"));
        hospital.setName(resultSet.getString("name"));
        hospital.setAddress(resultSet.getString("address"));
        hospital.setPhone(resultSet.getString("phone"));
        hospital.setEmail(resultSet.getString("email"));
        hospital.setCreatedAt(toLocalDateTime(resultSet.getTimestamp("created_at")));
        hospital.setUpdatedAt(toLocalDateTime(resultSet.getTimestamp("updated_at")));
        return hospital;
    }

    private Timestamp toTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
