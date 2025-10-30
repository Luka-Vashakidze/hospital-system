package com.hospital.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Doctor {

    @XmlAttribute
    private String id;

    @XmlElement
    private String fullName;

    @XmlElement
    private String specialty;

    @XmlElement
    private boolean onDuty;

    @XmlElement
    private LocalDate hiredDate;

    public Doctor() {
    }

    public Doctor(String id, String fullName, String specialty, boolean onDuty, LocalDate hiredDate) {
        this.id = id;
        this.fullName = fullName;
        this.specialty = specialty;
        this.onDuty = onDuty;
        this.hiredDate = hiredDate;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public boolean isOnDuty() {
        return onDuty;
    }

    public void setOnDuty(boolean onDuty) {
        this.onDuty = onDuty;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }
}





