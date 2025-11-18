package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;
import com.solvd.hospital.persistence.AppointmentRepository;
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

public class AppointmentRepositoryImpl implements AppointmentRepository {
    private static final String INSERT_SQL = "INSERT INTO appointments (department_id, doctor_id, patient_id, date_time, status, bill_amount) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM appointments WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM appointments";
    private static final String SELECT_BY_DOCTOR_SQL = "SELECT * FROM appointments WHERE doctor_id = ?";
    private static final String SELECT_BY_PATIENT_SQL = "SELECT * FROM appointments WHERE patient_id = ?";
    private static final String UPDATE_SQL = "UPDATE appointments SET department_id = ?, doctor_id = ?, patient_id = ?, date_time = ?, status = ?, bill_amount = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM appointments WHERE id = ?";
    private static final String SELECT_WITH_JOINS_SQL = """
            SELECT h.name AS hospital_name, d.name AS department_name, r.number AS room_number, doc.full_name AS doctor_name,
                   p.full_name AS patient_name, appt.date_time, tr.diagnosis, pr.medication, ins.policy_number,
                   it.name AS insurance_type, appt.bill_amount
            FROM appointments appt
            JOIN departments d ON appt.department_id = d.id
            JOIN hospitals h ON d.hospital_id = h.id
            JOIN doctors doc ON appt.doctor_id = doc.id
            LEFT JOIN patients p ON appt.patient_id = p.id
            LEFT JOIN rooms r ON r.department_id = d.id
            LEFT JOIN insurances ins ON p.insurance_id = ins.id
            LEFT JOIN insurance_types it ON ins.insurance_type_id = it.id
            LEFT JOIN treatments tr ON tr.patient_id = p.id AND tr.doctor_id = doc.id
            LEFT JOIN prescriptions pr ON pr.treatment_id = tr.id
            WHERE appt.date_time >= ?
            ORDER BY appt.date_time
            """;

    @Override
    public Appointment create(Appointment appointment) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, appointment.getDepartmentId());
            statement.setLong(2, appointment.getDoctorId());
            statement.setLong(3, appointment.getPatientId());
            statement.setTimestamp(4, toTimestamp(appointment.getDateTime()));
            statement.setString(5, appointment.getStatus());
            statement.setBigDecimal(6, appointment.getBillAmount());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert appointment", e);
        }
        return appointment;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapAppointment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find appointment by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                appointments.add(mapAppointment(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list appointments", e);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findByDoctor(Long doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_DOCTOR_SQL)) {
            statement.setLong(1, doctorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(mapAppointment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find appointments by doctor", e);
        }
        return appointments;
    }

    @Override
    public List<Appointment> findByPatient(Long patientId) {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PATIENT_SQL)) {
            statement.setLong(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    appointments.add(mapAppointment(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find appointments by patient", e);
        }
        return appointments;
    }

    @Override
    public List<AppointmentDetail> findUpcomingDetails(LocalDateTime fromDateTime) {
        List<AppointmentDetail> details = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_WITH_JOINS_SQL)) {
            statement.setTimestamp(1, toTimestamp(fromDateTime));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    details.add(mapAppointmentDetail(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch appointment details", e);
        }
        return details;
    }

    @Override
    public Appointment update(Appointment appointment) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, appointment.getDepartmentId());
            statement.setLong(2, appointment.getDoctorId());
            statement.setLong(3, appointment.getPatientId());
            statement.setTimestamp(4, toTimestamp(appointment.getDateTime()));
            statement.setString(5, appointment.getStatus());
            statement.setBigDecimal(6, appointment.getBillAmount());
            statement.setLong(7, appointment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update appointment", e);
        }
        return appointment;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete appointment", e);
        }
    }

    private Appointment mapAppointment(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getLong("id"));
        appointment.setDepartmentId(resultSet.getLong("department_id"));
        appointment.setDoctorId(resultSet.getLong("doctor_id"));
        appointment.setPatientId(resultSet.getLong("patient_id"));
        appointment.setDateTime(toLocalDateTime(resultSet.getTimestamp("date_time")));
        appointment.setStatus(resultSet.getString("status"));
        appointment.setBillAmount(resultSet.getBigDecimal("bill_amount"));
        return appointment;
    }

    private AppointmentDetail mapAppointmentDetail(ResultSet resultSet) throws SQLException {
        AppointmentDetail detail = new AppointmentDetail();
        detail.setHospitalName(resultSet.getString("hospital_name"));
        detail.setDepartmentName(resultSet.getString("department_name"));
        detail.setRoomNumber(resultSet.getString("room_number"));
        detail.setDoctorName(resultSet.getString("doctor_name"));
        detail.setPatientName(resultSet.getString("patient_name"));
        detail.setAppointmentTime(toLocalDateTime(resultSet.getTimestamp("date_time")));
        detail.setDiagnosis(resultSet.getString("diagnosis"));
        detail.setMedication(resultSet.getString("medication"));
        detail.setInsurancePolicy(resultSet.getString("policy_number"));
        detail.setInsuranceType(resultSet.getString("insurance_type"));
        detail.setBillAmount(resultSet.getBigDecimal("bill_amount"));
        return detail;
    }

    private Timestamp toTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
