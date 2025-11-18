package com.solvd.hospital.service.impl;

import com.solvd.hospital.domain.Room;
import com.solvd.hospital.persistence.RoomRepository;
import com.solvd.hospital.persistence.impl.RoomRepositoryImpl;
import com.solvd.hospital.service.RoomService;

import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl() {
        this.roomRepository = new RoomRepositoryImpl();
    }

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room save(Room room) {
        return roomRepository.create(room);
    }

    @Override
    public Optional<Room> get(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findByDepartment(Long departmentId) {
        return roomRepository.findByDepartment(departmentId);
    }

    @Override
    public Room update(Room room) {
        return roomRepository.update(room);
    }

    @Override
    public boolean remove(Long id) {
        return roomRepository.deleteById(id);
    }
}

