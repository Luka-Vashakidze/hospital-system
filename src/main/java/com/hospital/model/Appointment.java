package com.hospital.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class Appointment {

    @XmlElement
    private LocalDateTime dateTime;
    @XmlElement
    private String purpose;

    @XmlElement
    private double billAmount;

    public Appointment() {
    }

    public Appointment(LocalDateTime dateTime, String purpose, double billAmount) {
        this.dateTime = dateTime;
        this.purpose = purpose;
        this.billAmount = billAmount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }
}





