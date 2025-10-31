package com.solvd.hospital.jaxb;

import com.solvd.hospital.model.Hospital;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class JaxbHospital {
    public Hospital parse(InputStream xml) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Hospital.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Hospital) unmarshaller.unmarshal(xml);
    }
}





