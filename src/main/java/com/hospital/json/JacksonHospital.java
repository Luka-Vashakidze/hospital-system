package com.hospital.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hospital.model.Hospital;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.io.InputStream;
import java.util.List;

public class JacksonHospital {
    public Hospital parse(InputStream json) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(json, Hospital.class);
    }

    public void jsonPathPrints(InputStream json) throws Exception {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json.readAllBytes());
        List<String> departmentCodes = JsonPath.read(document, "$.departments[*].code");
        List<String> doctorNames = JsonPath.read(document, "$.departments[*].doctors[*].fullName");
        List<String> patientIds = JsonPath.read(document, "$.departments[*].patients[*].id");
        List<Boolean> insuredFlags = JsonPath.read(document, "$.departments[*].patients[*].insured");
        List<Double> billAmounts = JsonPath.read(document, "$.departments[*].patients[*].appointments[*].billAmount");

        System.out.println("Department codes: " + departmentCodes);
        System.out.println("Doctor names: " + doctorNames);
        System.out.println("Patient IDs: " + patientIds);
        System.out.println("Insured flags: " + insuredFlags);
        System.out.println("Bill amounts: " + billAmounts);
    }
}





