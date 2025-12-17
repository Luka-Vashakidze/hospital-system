package com.solvd.hospital.service;

import com.solvd.hospital.domain.Room;

import java.util.List;

public interface RoomService {

    Room save(Room room);

    Room get(Long id);

    List<Room> getAll();

    List<Room> findByDepartment(Long departmentId);

    Room update(Room room);

    boolean remove(Long id);
}
