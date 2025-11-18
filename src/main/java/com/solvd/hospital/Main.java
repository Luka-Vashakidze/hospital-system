package com.solvd.hospital;

import com.solvd.hospital.controller.HospitalController;
import com.solvd.hospital.jaxb.JaxbHospital;
import com.solvd.hospital.json.JacksonHospital;
import com.solvd.hospital.model.Hospital;
import com.solvd.hospital.parser.StaxHospitalParser;
import com.solvd.hospital.service.impl.AppointmentServiceImpl;
import com.solvd.hospital.service.impl.DepartmentServiceImpl;
import com.solvd.hospital.service.impl.DoctorServiceImpl;
import com.solvd.hospital.service.impl.HospitalServiceImpl;
import com.solvd.hospital.service.impl.PatientServiceImpl;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        try (InputStream xsd = cl.getResourceAsStream("hospital.xsd");
             InputStream xmlForValidation = cl.getResourceAsStream("hospital.xml");
             InputStream xmlForStax = cl.getResourceAsStream("hospital.xml");
             InputStream xmlForJaxb = cl.getResourceAsStream("hospital.xml");
             InputStream jsonForJackson = cl.getResourceAsStream("hospital.json");
             InputStream jsonForJsonPath = cl.getResourceAsStream("hospital.json")) {

            if (xsd == null || xmlForValidation == null || xmlForStax == null || xmlForJaxb == null || jsonForJackson == null || jsonForJsonPath == null) {
                throw new IllegalStateException("resources not found");
            }

            StaxHospitalParser stax = new StaxHospitalParser();
            stax.validateAgainstXsd(xmlForValidation, xsd);
            System.out.println("XML validated");

            Hospital hospitalFromStax = stax.parse(xmlForStax);
            System.out.println("StAX parsed hospital name: " + hospitalFromStax.getName());

            JaxbHospital jaxb = new JaxbHospital();
            Hospital hospitalFromJaxb = jaxb.parse(xmlForJaxb);
            System.out.println("JAXB parsed departments: " + hospitalFromJaxb.getDepartments().size());

            JacksonHospital jackson = new JacksonHospital();
            Hospital hospitalFromJson = jackson.parse(jsonForJackson);
            System.out.println("Jackson parsed hospital: " + hospitalFromJson.getName());

            jackson.jsonPathPrints(jsonForJsonPath);
        }

        HospitalController controller = new HospitalController(
                new HospitalServiceImpl(),
                new DepartmentServiceImpl(),
                new DoctorServiceImpl(),
                new PatientServiceImpl(),
                new AppointmentServiceImpl()
        );
        System.out.println("MVC task completed: " + controller.getClass().getSimpleName()
                + " is ready with JDBC-backed services.");
    }
}





