package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.Patient;
import com.solvd.hospital.persistence.PatientRepository;
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

public class PatientRepositoryImpl implements PatientRepository {
    private static final String INSERT_SQL = "INSERT INTO patients (full_name, birth_date, gender, phone, insurance_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM patients WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM patients";
    private static final String SELECT_BY_INSURANCE_SQL = "SELECT * FROM patients WHERE insurance_id = ?";
    private static final String UPDATE_SQL = "UPDATE patients SET full_name = ?, birth_date = ?, gender = ?, phone = ?, insurance_id = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM patients WHERE id = ?";

    @Override
    public Patient create(Patient patient) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, patient.getFullName());
            statement.setDate(2, toDate(patient.getBirthDate()));
            statement.setString(3, patient.getGender());
            statement.setString(4, patient.getPhone());
            statement.setLong(5, patient.getInsuranceId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patient.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert patient", e);
        }
        return patient;
    }

    @Override
    public Optional<Patient> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapPatient(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find patient by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                patients.add(mapPatient(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list patients", e);
        }
        return patients;
    }

    @Override
    public List<Patient> findByInsurance(Long insuranceId) {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_INSURANCE_SQL)) {
            statement.setLong(1, insuranceId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(mapPatient(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find patients by insurance", e);
        }
        return patients;
    }

    @Override
    public Patient update(Patient patient) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, patient.getFullName());
            statement.setDate(2, toDate(patient.getBirthDate()));
            statement.setString(3, patient.getGender());
            statement.setString(4, patient.getPhone());
            statement.setLong(5, patient.getInsuranceId());
            statement.setLong(6, patient.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update patient", e);
        }
        return patient;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete patient", e);
        }
    }

    private Patient mapPatient(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setFullName(resultSet.getString("full_name"));
        patient.setBirthDate(toLocalDate(resultSet.getDate("birth_date")));
        patient.setGender(resultSet.getString("gender"));
        patient.setPhone(resultSet.getString("phone"));
        patient.setInsuranceId(resultSet.getLong("insurance_id"));
        return patient;
    }

    private Date toDate(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }

    private LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
