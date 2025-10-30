package com.hospital.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Patient {

    @XmlAttribute
    private String id;

    @XmlElement
    private String fullName;

    @XmlElement
    private LocalDate birthDate;

    @XmlElement
    private boolean insured;

    @XmlElementWrapper(name = "appointments")
    @XmlElement(name = "appointment")
    private List<Appointment> appointments = new ArrayList<>();

    @XmlElement
    private Admission admission;

    @XmlElement
    private Treatment treatment;

    public Patient() {
    }

    public Patient(String id, String fullName, LocalDate birthDate, boolean insured) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.insured = insured;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isInsured() {
        return insured;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
}


