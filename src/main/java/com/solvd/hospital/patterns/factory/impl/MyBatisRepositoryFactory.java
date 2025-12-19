package com.solvd.hospital.patterns.factory.impl;

import com.solvd.hospital.patterns.factory.RepositoryFactory;
import com.solvd.hospital.persistence.AppointmentRepository;
import com.solvd.hospital.persistence.DepartmentRepository;
import com.solvd.hospital.persistence.DoctorRepository;
import com.solvd.hospital.persistence.HospitalRepository;
import com.solvd.hospital.persistence.InsuranceRepository;
import com.solvd.hospital.persistence.InsuranceTypeRepository;
import com.solvd.hospital.persistence.PatientRepository;
import com.solvd.hospital.persistence.RoomRepository;
import com.solvd.hospital.persistence.mybatis.impl.AppointmentRepositoryMyBatisImpl;
import com.solvd.hospital.persistence.mybatis.impl.DepartmentRepositoryMyBatisImpl;
import com.solvd.hospital.persistence.mybatis.impl.DoctorRepositoryMyBatisImpl;
import com.solvd.hospital.persistence.mybatis.impl.HospitalRepositoryMyBatisImpl;
import com.solvd.hospital.persistence.mybatis.impl.InsuranceRepositoryMyBatisImpl;
import com.solvd.hospital.persistence.mybatis.impl.InsuranceTypeRepositoryMyBatisImpl;
import com.solvd.hospital.persistence.mybatis.impl.PatientRepositoryMyBatisImpl;
import com.solvd.hospital.persistence.mybatis.impl.RoomRepositoryMyBatisImpl;

public class MyBatisRepositoryFactory implements RepositoryFactory {
    @Override
    public HospitalRepository hospitalRepository() {
        return new HospitalRepositoryMyBatisImpl();
    }

    @Override
    public DepartmentRepository departmentRepository() {
        return new DepartmentRepositoryMyBatisImpl();
    }

    @Override
    public DoctorRepository doctorRepository() {
        return new DoctorRepositoryMyBatisImpl();
    }

    @Override
    public PatientRepository patientRepository() {
        return new PatientRepositoryMyBatisImpl();
    }

    @Override
    public RoomRepository roomRepository() {
        return new RoomRepositoryMyBatisImpl();
    }

    @Override
    public InsuranceRepository insuranceRepository() {
        return new InsuranceRepositoryMyBatisImpl();
    }

    @Override
    public InsuranceTypeRepository insuranceTypeRepository() {
        return new InsuranceTypeRepositoryMyBatisImpl();
    }

    @Override
    public AppointmentRepository appointmentRepository() {
        return new AppointmentRepositoryMyBatisImpl();
    }
}
