package com.solvd.hospital.controller;

import com.solvd.hospital.domain.Appointment;
import com.solvd.hospital.domain.AppointmentDetail;
import com.solvd.hospital.domain.Department;
import com.solvd.hospital.domain.Doctor;
import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.domain.Patient;
import com.solvd.hospital.service.AppointmentService;
import com.solvd.hospital.service.DepartmentService;
import com.solvd.hospital.service.DoctorService;
import com.solvd.hospital.service.HospitalService;
import com.solvd.hospital.service.PatientService;

import java.time.LocalDateTime;
import java.util.List;

public class HospitalController {
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public HospitalController(HospitalService hospitalService,
                              DepartmentService departmentService,
                              DoctorService doctorService,
                              PatientService patientService,
                              AppointmentService appointmentService) {
        this.hospitalService = hospitalService;
        this.departmentService = departmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    public Hospital registerHospital(Hospital hospital) {
        return hospitalService.save(hospital);
    }

    public List<Department> listDepartments(Long hospitalId) {
        return hospitalId == null
                ? departmentService.getAll()
                : departmentService.findByHospital(hospitalId);
    }

    public List<Doctor> listDoctors(Long departmentId) {
        return departmentId == null
                ? doctorService.getAll()
                : doctorService.findByDepartment(departmentId);
    }

    public Patient registerPatient(Patient patient) {
        return patientService.save(patient);
    }

    public List<Patient> patientsByInsurance(Long insuranceId) {
        return insuranceId == null
                ? patientService.getAll()
                : patientService.findByInsurance(insuranceId);
    }

    public List<Appointment> doctorSchedule(Long doctorId) {
        return appointmentService.findByDoctor(doctorId);
    }

    public List<AppointmentDetail> upcomingAppointments(LocalDateTime fromDateTime) {
        return appointmentService.upcomingDetails(fromDateTime);
    }
}
