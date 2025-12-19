package com.solvd.hospital.persistence.mybatis;

import com.solvd.hospital.domain.Room;

import java.util.List;

public interface RoomMapper {

    void insert(Room room);

    Room selectById(Long id);

    List<Room> selectAll();

    List<Room> selectByDepartment(Long departmentId);

    int update(Room room);

    int delete(Long id);
}
