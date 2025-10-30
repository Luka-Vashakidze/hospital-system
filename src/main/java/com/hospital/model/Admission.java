package com.hospital.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class Admission {

    @XmlElement
    private LocalDate admittedDate; // date

    @XmlElement
    private Room room;

    public Admission() {
    }

    public Admission(LocalDate admittedDate, Room room) {
        this.admittedDate = admittedDate;
        this.room = room;
    }

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
}





