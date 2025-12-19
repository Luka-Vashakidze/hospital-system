package com.solvd.hospital.persistence.mybatis.impl;

import com.solvd.hospital.domain.Room;
import com.solvd.hospital.persistence.RoomRepository;
import com.solvd.hospital.persistence.mybatis.MyBatisConfig;
import com.solvd.hospital.persistence.mybatis.RoomMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Optional;

public class RoomRepositoryMyBatisImpl implements RoomRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public RoomRepositoryMyBatisImpl() {
        this(MyBatisConfig.getSqlSessionFactory());
    }

    public RoomRepositoryMyBatisImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Room create(Room room) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            RoomMapper mapper = session.getMapper(RoomMapper.class);
            mapper.insert(room);
        }
        return room;
    }

    @Override
    public Optional<Room> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            RoomMapper mapper = session.getMapper(RoomMapper.class);
            return Optional.ofNullable(mapper.selectById(id));
        }
    }

    @Override
    public List<Room> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            RoomMapper mapper = session.getMapper(RoomMapper.class);
            return mapper.selectAll();
        }
    }

    @Override
    public List<Room> findByDepartment(Long departmentId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            RoomMapper mapper = session.getMapper(RoomMapper.class);
            return mapper.selectByDepartment(departmentId);
        }
    }

    @Override
    public Room update(Room room) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            RoomMapper mapper = session.getMapper(RoomMapper.class);
            mapper.update(room);
        }
        return room;
    }

    @Override
    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            RoomMapper mapper = session.getMapper(RoomMapper.class);
            return mapper.delete(id) > 0;
        }
    }
}
