package com.solvd.hospital.persistence;

import com.solvd.hospital.domain.Room;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {
    List<Room> findByDepartment(Long departmentId);
}
