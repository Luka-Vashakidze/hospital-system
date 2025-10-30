package com.hospital.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Prescription {

    @XmlAttribute
    private String id;

    @XmlElement
    private String medication;

    @XmlElement
    private int dosageMg;

    public Prescription() {
    }

    public Prescription(String id, String medication, int dosageMg) {
        this.id = id;
        this.medication = medication;
        this.dosageMg = dosageMg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public int getDosageMg() {
        return dosageMg;
    }

    public void setDosageMg(int dosageMg) {
        this.dosageMg = dosageMg;
    }
}





