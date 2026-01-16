package org.example;

import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main
{
    public void loadFirstTimeData(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // DEPARTMENTS
        Department cardiology = new Department();
        cardiology.name = "Cardiology";
        cardiology.floorNumber = 2;

        Department surgery = new Department();
        surgery.name = "Surgery";
        surgery.floorNumber = 4;

        session.save(cardiology);
        session.save(surgery);

        //DOCTORS
        Doctor d1 = new Doctor("Anna", null, "Nowak", "Polish",
                LocalDate.of(1980, 3, 10), StateDoctor.ACTIVE, "Cardiologist");
        d1.department = cardiology;

        Doctor d2 = new Doctor("Piotr", null, "Kaczmarek", "Polish",
                LocalDate.of(1975, 7, 22), StateDoctor.ACTIVE, "Cardiac Surgeon");
        d2.department = cardiology;

        Doctor d3 = new Doctor("Tomasz", null, "Lewandowski", "Polish",
                LocalDate.of(1983, 1, 5), StateDoctor.ACTIVE, "Surgeon");
        d3.department = surgery;

        Doctor d4 = new Doctor("Katarzyna", null, "Mazur", "Polish",
                LocalDate.of(1988, 11, 30), StateDoctor.ACTIVE, "Anesthesiologist");
        d4.department = surgery;

        Doctor d5 = new Doctor("Marek", null, "Zielinski", "Polish",
                LocalDate.of(1990, 6, 14), StateDoctor.IN_TRAINING, "Resident");
        d5.department = surgery;

        cardiology.HeadOfDepartment = d1;
        surgery.HeadOfDepartment = d3;

        session.save(d1);
        session.save(d2);
        session.save(d3);
        session.save(d4);
        session.save(d5);

        //PATIENTS
        List<Patient> patients = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            Patient p = new Patient();
            p.name = "PatientName" + i;
            p.surname = "PatientSurname" + i;
            p.nationality = "Polish";
            p.dateOfBirth = LocalDate.of(1990 + i, 1, 1);
            p.sex = (i % 2 == 0) ? "Female" : "Male";
            p.state = StatePatient.Treated;
            p.weight = 70 + i;
            p.height = 170 + i;

            session.save(p);
            patients.add(p);
        }

        //APPOINTMENTS
        List<Patient> patients2 = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            Patient p = new Patient();
            p.name = "PatientName" + i;
            p.surname = "PatientSurname" + i;
            p.nationality = "Polish";
            p.dateOfBirth = LocalDate.of(1990 + i, 1, 1);
            p.sex = (i % 2 == 0) ? "Female" : "Male";
            p.state = StatePatient.Treated;
            p.weight = 70 + i;
            p.height = 170 + i;

            session.save(p);
            patients2.add(p);
        }

        //AMBULANCES
        FirstAidVan van1 = new FirstAidVan();
        van1.model = "FA Van A";
        van1.plateNumber = 1001;
        van1.capacity = 2;

        FirstAidVan van2 = new FirstAidVan();
        van2.model = "FA Van B";
        van2.plateNumber = 1002;
        van2.capacity = 2;

        FirstAidHelicopter heli1 = new FirstAidHelicopter();
        heli1.model = "FA Helicopter A";
        heli1.plateNumber = 2001;
        heli1.capacity = 3;

        FirstAidHelicopter heli2 = new FirstAidHelicopter();
        heli2.model = "FA Helicopter B";
        heli2.plateNumber = 2002;
        heli2.capacity = 3;

        session.save(van1);
        session.save(van2);
        session.save(heli1);
        session.save(heli2);

        //PATIENT–AMBULANCE TRANSPORTS
        for (int i = 0; i < 6; i++) {
            Patient_Ambulance pa = new Patient_Ambulance();
            pa.date = new Date();
            pa.dateArrivalAtHospital = new Date();
            pa.locationOfAccident = "Street " + (i + 1);
            pa.status = StatePatient.Delivered;

            pa.patient = patients.get(i);
            pa.ambulance = (i % 2 == 0) ? van1 : heli1;

            session.save(pa);
        }

        session.getTransaction().commit();
        session.close();

        session.getTransaction().commit();
        session.close();
    }
    public void testshow1(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Person p = session.get(Person.class, 1);
        System.out.println(p);

        session.getTransaction().commit();
        session.close();
    }
    public static void main( String[] args )
    {

    }
}
