package com.solvd.hospital.parser;

import com.solvd.hospital.model.Hospital;
import com.solvd.hospital.model.Department;
import com.solvd.hospital.model.Doctor;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
import java.time.LocalDate;

/**
  * Example XPaths:
 * 1. /hospital/departments/department[@name='Emergency']
 * 2. /hospital/departments/department/doctors/doctor[@id='D001']
 * 3. /hospital/patients/patient[age &gt; 60]
 * 4. /hospital/departments/department/rooms/room[isAvailable='true']
 * 5. /hospital/patients/patient[treatments/treatment/type='Surgery']
 */
public class StaxHospitalParser {

    public void validateAgainstXsd(InputStream xml, InputStream xsd) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new javax.xml.transform.stream.StreamSource(xsd));
        Validator validator = schema.newValidator();
        validator.validate(new javax.xml.transform.stream.StreamSource(xml));
    }

    public Hospital parse(InputStream xmlStream) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(xmlStream);

        Hospital hospital = null;
        Department department = null;
        Doctor doctor = null;
        String content = null;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String elementName = startElement.getName().getLocalPart();

                if (elementName.equals("hospital")) {
                    hospital = new Hospital();
                }
                if (elementName.equals("department")) {
                    department = new Department();
                    String code = startElement.getAttributeByName(new javax.xml.namespace.QName("code")).getValue();
                    department.setCode(code);
                }
                if (elementName.equals("doctor")) {
                    doctor = new Doctor();
                    String id = startElement.getAttributeByName(new javax.xml.namespace.QName("id")).getValue();
                    doctor.setId(id);
                }
            }

            if (event.isCharacters()) {
                content = event.asCharacters().getData().trim();
            }

            if (event.isEndElement()) {
                String elementName = event.asEndElement().getName().getLocalPart();

                if (elementName.equals("name") && department == null) {
                    hospital.setName(content);
                }
                if (elementName.equals("name") && department != null && doctor == null) {
                    department.setName(content);
                }
                if (elementName.equals("fullName") && doctor != null) {
                    doctor.setFullName(content);
                }
                if (elementName.equals("specialty")) {
                    doctor.setSpecialty(content);
                }
                if (elementName.equals("onDuty")) {
                    doctor.setOnDuty(Boolean.parseBoolean(content));
                }
                if (elementName.equals("hiredDate")) {
                    doctor.setHiredDate(LocalDate.parse(content));
                }
                if (elementName.equals("doctor")) {
                    department.getDoctors().add(doctor);
                    doctor = null;
                }
                if (elementName.equals("department")) {
                    hospital.getDepartments().add(department);
                    department = null;
                }
            }
        }

        return hospital;
    }
}