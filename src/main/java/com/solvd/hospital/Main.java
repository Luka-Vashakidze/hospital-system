package com.solvd.hospital;

import com.solvd.hospital.controller.HospitalController;
import com.solvd.hospital.domain.Hospital;
import com.solvd.hospital.patterns.facade.HospitalFacade;
import com.solvd.hospital.patterns.factory.RepositoryFactory;
import com.solvd.hospital.patterns.factory.ServiceFactory;
import com.solvd.hospital.patterns.factory.impl.MyBatisRepositoryFactory;
import com.solvd.hospital.patterns.factory.impl.MyBatisServiceFactory;
import com.solvd.hospital.patterns.listener.AppointmentEventPublisher;
import com.solvd.hospital.patterns.notification.BasicNotificationService;
import com.solvd.hospital.patterns.notification.LoggingNotificationDecorator;
import com.solvd.hospital.patterns.notification.NotificationService;
import com.solvd.hospital.patterns.strategy.BillingStrategy;
import com.solvd.hospital.patterns.strategy.InsuranceBillingStrategy;
import com.solvd.hospital.patterns.strategy.StandardBillingStrategy;
import com.solvd.hospital.service.parser.StaxHospitalParser;
import com.solvd.hospital.service.parser.jaxb.JaxbHospital;
import com.solvd.hospital.service.parser.json.JacksonHospital;
import com.solvd.hospital.service.impl.AppointmentServiceImpl;
import com.solvd.hospital.service.impl.DepartmentServiceImpl;
import com.solvd.hospital.service.impl.DoctorServiceImpl;
import com.solvd.hospital.service.impl.HospitalServiceImpl;
import com.solvd.hospital.service.impl.PatientServiceImpl;

import java.io.InputStream;
import java.math.BigDecimal;

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
                + " is ready with MyBatis-backed services.");

        RepositoryFactory repositoryFactory = new MyBatisRepositoryFactory();
        ServiceFactory serviceFactory = new MyBatisServiceFactory(repositoryFactory);
        AppointmentEventPublisher eventPublisher = new AppointmentEventPublisher();
        NotificationService notificationService = new LoggingNotificationDecorator(new BasicNotificationService());
        BillingStrategy billingStrategy = new InsuranceBillingStrategy(new BigDecimal("0.90"));
        HospitalFacade facade = new HospitalFacade(
                serviceFactory,
                billingStrategy,
                eventPublisher,
                notificationService,
                true
        );
        System.out.println("Facade pattern initialized: " + facade.getClass().getSimpleName()
                + " wires services, strategies, listeners, and proxy decorators.");
    }
}



