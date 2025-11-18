package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.Doctor;
import com.solvd.hospital.persistence.DoctorRepository;
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

public class DoctorRepositoryImpl implements DoctorRepository {
    private static final String INSERT_SQL = "INSERT INTO doctors (department_id, full_name, specialty, phone, email, hired_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM doctors WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM doctors";
    private static final String SELECT_BY_DEPARTMENT_SQL = "SELECT * FROM doctors WHERE department_id = ?";
    private static final String UPDATE_SQL = "UPDATE doctors SET department_id = ?, full_name = ?, specialty = ?, phone = ?, email = ?, hired_date = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM doctors WHERE id = ?";

    @Override
    public Doctor create(Doctor doctor) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, doctor.getDepartmentId());
            statement.setString(2, doctor.getFullName());
            statement.setString(3, doctor.getSpecialty());
            statement.setString(4, doctor.getPhone());
            statement.setString(5, doctor.getEmail());
            statement.setDate(6, toDate(doctor.getHiredDate()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    doctor.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert doctor", e);
        }
        return doctor;
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapDoctor(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find doctor by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                doctors.add(mapDoctor(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list doctors", e);
        }
        return doctors;
    }

    @Override
    public List<Doctor> findByDepartmentId(Long departmentId) {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_DEPARTMENT_SQL)) {
            statement.setLong(1, departmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    doctors.add(mapDoctor(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find doctors by department", e);
        }
        return doctors;
    }

    @Override
    public Doctor update(Doctor doctor) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, doctor.getDepartmentId());
            statement.setString(2, doctor.getFullName());
            statement.setString(3, doctor.getSpecialty());
            statement.setString(4, doctor.getPhone());
            statement.setString(5, doctor.getEmail());
            statement.setDate(6, toDate(doctor.getHiredDate()));
            statement.setLong(7, doctor.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update doctor", e);
        }
        return doctor;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete doctor", e);
        }
    }

    private Doctor mapDoctor(ResultSet resultSet) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getLong("id"));
        doctor.setDepartmentId(resultSet.getLong("department_id"));
        doctor.setFullName(resultSet.getString("full_name"));
        doctor.setSpecialty(resultSet.getString("specialty"));
        doctor.setPhone(resultSet.getString("phone"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setHiredDate(toLocalDate(resultSet.getDate("hired_date")));
        return doctor;
    }

    private Date toDate(LocalDate localDate) {
        return localDate == null ? null : Date.valueOf(localDate);
    }

    private LocalDate toLocalDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
