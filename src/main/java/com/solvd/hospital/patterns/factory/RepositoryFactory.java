package com.solvd.hospital.patterns.factory;

import com.solvd.hospital.persistence.AppointmentRepository;
import com.solvd.hospital.persistence.DepartmentRepository;
import com.solvd.hospital.persistence.DoctorRepository;
import com.solvd.hospital.persistence.HospitalRepository;
import com.solvd.hospital.persistence.InsuranceRepository;
import com.solvd.hospital.persistence.InsuranceTypeRepository;
import com.solvd.hospital.persistence.PatientRepository;
import com.solvd.hospital.persistence.RoomRepository;

public interface RepositoryFactory {
    HospitalRepository hospitalRepository();
    DepartmentRepository departmentRepository();
    DoctorRepository doctorRepository();
    PatientRepository patientRepository();
    RoomRepository roomRepository();
    InsuranceRepository insuranceRepository();
    InsuranceTypeRepository insuranceTypeRepository();
    AppointmentRepository appointmentRepository();
}
