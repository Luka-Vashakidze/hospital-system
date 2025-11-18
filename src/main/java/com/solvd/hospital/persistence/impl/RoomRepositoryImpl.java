package com.solvd.hospital.persistence.impl;

import com.solvd.hospital.domain.Room;
import com.solvd.hospital.persistence.RoomRepository;
import com.solvd.hospital.persistence.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomRepositoryImpl implements RoomRepository {
    private static final String INSERT_SQL = "INSERT INTO rooms (department_id, number, is_available) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM rooms WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM rooms";
    private static final String SELECT_BY_DEPARTMENT_SQL = "SELECT * FROM rooms WHERE department_id = ?";
    private static final String UPDATE_SQL = "UPDATE rooms SET department_id = ?, number = ?, is_available = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM rooms WHERE id = ?";

    @Override
    public Room create(Room room) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, room.getDepartmentId());
            statement.setString(2, room.getNumber());
            statement.setBoolean(3, room.isAvailable());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    room.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert room", e);
        }
        return room;
    }

    @Override
    public Optional<Room> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find room by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rooms.add(mapRoom(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list rooms", e);
        }
        return rooms;
    }

    @Override
    public List<Room> findByDepartment(Long departmentId) {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_DEPARTMENT_SQL)) {
            statement.setLong(1, departmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rooms.add(mapRoom(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find rooms by department", e);
        }
        return rooms;
    }

    @Override
    public Room update(Room room) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setLong(1, room.getDepartmentId());
            statement.setString(2, room.getNumber());
            statement.setBoolean(3, room.isAvailable());
            statement.setLong(4, room.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update room", e);
        }
        return room;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete room", e);
        }
    }

    private Room mapRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setId(resultSet.getLong("id"));
        room.setDepartmentId(resultSet.getLong("department_id"));
        room.setNumber(resultSet.getString("number"));
        room.setAvailable(resultSet.getBoolean("is_available"));
        return room;
    }
}
