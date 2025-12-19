package com.solvd.hospital.patterns.factory;

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

public interface ServiceFactory {
    HospitalService hospitalService();
    DepartmentService departmentService();
    DoctorService doctorService();
    PatientService patientService();
    RoomService roomService();
    InsuranceService insuranceService();
    InsuranceTypeService insuranceTypeService();
    AppointmentService appointmentService(BillingStrategy billingStrategy, AppointmentEventPublisher publisher);
}
