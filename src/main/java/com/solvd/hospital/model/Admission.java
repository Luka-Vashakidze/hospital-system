package com.solvd.hospital.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Admission {

    @XmlAttribute
    private Long id;

    @XmlElement
    private LocalDate admittedDate;

    @XmlElement
    private Room room;

    public LocalDate getAdmittedDate() {
        return admittedDate;
    }

    public void setAdmittedDate(LocalDate admittedDate) {
        this.admittedDate = admittedDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}





