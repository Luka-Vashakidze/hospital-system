package com.solvd.hospital.patterns.factory.impl;

import com.solvd.hospital.patterns.factory.RepositoryFactory;
import com.solvd.hospital.patterns.factory.ServiceFactory;
import com.solvd.hospital.patterns.listener.AppointmentEventPublisher;
import com.solvd.hospital.patterns.strategy.BillingStrategy;
import com.solvd.hospital.service.AppointmentService;
import com.solvd.hospital.service.DepartmentService;
import com.solvd.hospital.service.DoctorService;
import com.solvd.hospital.service.HospitalService;
import com.solvd.hospital.service.InsuranceService;
import com.solvd.hospital.service.InsuranceTypeService;
import com.solvd.hospital.service.PatientService;
import com.solvd.hospital.service.RoomService;
import com.solvd.hospital.service.impl.AppointmentServiceImpl;
import com.solvd.hospital.service.impl.DepartmentServiceImpl;
import com.solvd.hospital.service.impl.DoctorServiceImpl;
import com.solvd.hospital.service.impl.HospitalServiceImpl;
import com.solvd.hospital.service.impl.InsuranceServiceImpl;
import com.solvd.hospital.service.impl.InsuranceTypeServiceImpl;
import com.solvd.hospital.service.impl.PatientServiceImpl;
import com.solvd.hospital.service.impl.RoomServiceImpl;

public class MyBatisServiceFactory implements ServiceFactory {

    private final RepositoryFactory repositoryFactory;

    public MyBatisServiceFactory(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public HospitalService hospitalService() {
        return new HospitalServiceImpl(repositoryFactory.hospitalRepository(), departmentService());
    }

    @Override
    public DepartmentService departmentService() {
        return new DepartmentServiceImpl(repositoryFactory.departmentRepository());
    }

    @Override
    public DoctorService doctorService() {
        return new DoctorServiceImpl(repositoryFactory.doctorRepository());
    }

    @Override
    public PatientService patientService() {
        return new PatientServiceImpl(repositoryFactory.patientRepository());
    }

    @Override
    public RoomService roomService() {
        return new RoomServiceImpl(repositoryFactory.roomRepository());
    }

    @Override
    public InsuranceService insuranceService() {
        return new InsuranceServiceImpl(repositoryFactory.insuranceRepository());
    }

    @Override
    public InsuranceTypeService insuranceTypeService() {
        return new InsuranceTypeServiceImpl(repositoryFactory.insuranceTypeRepository());
    }

    @Override
    public AppointmentService appointmentService(BillingStrategy billingStrategy, AppointmentEventPublisher publisher) {
        return new AppointmentServiceImpl(repositoryFactory.appointmentRepository(), billingStrategy, publisher);
    }
}
