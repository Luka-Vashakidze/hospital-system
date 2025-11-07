package com.solvd.hospital.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Prescription {

    @XmlAttribute
    private Long id;

    @XmlElement
    private String medication;

    @XmlElement
    private int dosageMg;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
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





