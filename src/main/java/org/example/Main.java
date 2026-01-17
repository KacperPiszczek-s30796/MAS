package org.example;

import org.example.Entities.*;
import org.example.Enums.*;
import org.example.GUI.MainFrame;
import org.example.Utilities.HibernateUtil;
import org.hibernate.Session;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main
{
    public static void loadFirstTimeData(){
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
            p.state = StatePatient.NeedingHelp;
            p.weight = 70 + i;
            p.height = 170 + i;

            session.save(p);
            patients.add(p);
        }

        //APPOINTMENTS
        for (int i = 0; i < 10; i++) {
            Apointment a = new Apointment();
            a.date = new Date();
            a.time = LocalTime.of(9 + (i % 5), 0);
            a.duration = LocalTime.of(0, 30);
            a.status = StatusAppointment.reserved;
            a.observations = "Routine check";

            a.patient = patients.get(i % patients.size());
            a.doctor = (i % 2 == 0) ? d1 : d3;

            session.save(a);
        }

        //AMBULANCES
        FirstAidVan van1 = new FirstAidVan();
        van1.model = "FA Van";
        van1.plateNumber = 1001;
        van1.capacity = 2;
        van1.cargoVolume = 20;
        van1.terrain = Terrain.urban;
        van1.maxSpeed = 100;
        van1.supplyDescription = "yes";

        SurgicalVan van2 = new SurgicalVan();
        van2.model = "S Van";
        van2.plateNumber = 1002;
        van2.capacity = 2;
        van2.cargoVolume = 40;
        van2.terrain = Terrain.rural;
        van2.maxSpeed = 120;
        van2.equipmentDescription = "anesthesia equipment present";

        FirstAidBoat boat1 = new FirstAidBoat();
        boat1.model = "FA Boat";
        boat1.plateNumber = 1001;
        boat1.capacity = 2;
        boat1.length = 6;
        boat1.maxSpeed = 60;
        boat1.isEquipmentWaterproof = true;
        boat1.supplyDescription = "no";

        SurgicalBoat boat2 = new SurgicalBoat();
        boat2.model = "S Boat";
        boat2.plateNumber = 1001;
        boat2.capacity = 2;
        boat2.length = 8;
        boat2.maxSpeed = 50;
        boat2.isEquipmentWaterproof = true;
        boat2.equipmentDescription = "stability equipment present";

        FirstAidHelicopter heli1 = new FirstAidHelicopter();
        heli1.model = "FA Helicopter";
        heli1.plateNumber = 2001;
        heli1.capacity = 3;
        heli1.maxAltitude = 3;
        heli1.requiredAreaToLand = 100;
        heli1.supplyDescription = "yes";

        SurgicalHelicopter heli2 = new SurgicalHelicopter();
        heli2.model = "S Helicopter";
        heli2.plateNumber = 2002;
        heli2.capacity = 3;
        heli2.maxAltitude = 2;
        heli2.requiredAreaToLand = 120;
        heli2.equipmentDescription = "high precision equipment present";

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
            pa.status = patients.get(i).state;

            pa.patient = patients.get(i);
            pa.ambulance = (i % 2 == 0) ? van1 : heli1;

            session.save(pa);
        }

        //Paramedic
        Paramedic pm1 = new Paramedic();
        pm1.name = "Adam";
        pm1.surname = "Kaczmarek";
        pm1.nationality = "Polish";
        pm1.dateOfBirth = LocalDate.of(1992, 4, 12);
        pm1.CPRNumber = 10001;
        pm1.ALSNumber = 20001;

        Paramedic pm2 = new Paramedic();
        pm2.name = "Marta";
        pm2.surname = "Dabrowska";
        pm2.nationality = "Polish";
        pm2.dateOfBirth = LocalDate.of(1995, 9, 3);
        pm2.CPRNumber = 10002;
        pm2.ALSNumber = 20002;

        Paramedic pm3 = new Paramedic();
        pm3.name = "Krzysztof";
        pm3.surname = "Wojcik";
        pm3.nationality = "Polish";
        pm3.dateOfBirth = LocalDate.of(1988, 6, 20);
        pm3.CPRNumber = 10003;
        pm3.ALSNumber = 20003;

        Paramedic pm4 = new Paramedic();
        pm4.name = "Natalia";
        pm4.surname = "Kaminska";
        pm4.nationality = "Polish";
        pm4.dateOfBirth = LocalDate.of(1993, 1, 17);
        pm4.CPRNumber = 10004;
        pm4.ALSNumber = 20004;

        Paramedic pm5 = new Paramedic();
        pm5.name = "Pawel";
        pm5.surname = "Lewicki";
        pm5.nationality = "Polish";
        pm5.dateOfBirth = LocalDate.of(1990, 12, 1);
        pm5.CPRNumber = 10005;
        pm5.ALSNumber = 20005;

        session.save(pm1);
        session.save(pm2);
        session.save(pm3);
        session.save(pm4);
        session.save(pm5);

        //Driver
        Driver dr1 = new Driver();
        dr1.name = "Michal";
        dr1.surname = "Nowicki";
        dr1.nationality = "Polish";
        dr1.dateOfBirth = LocalDate.of(1985, 8, 14);
        dr1.licenseType = LicenseType.manual;
        dr1.licenseNumber = 50001;
        dr1.drivingPermit = "PL-C-50001";

        Driver dr2 = new Driver();
        dr2.name = "Karolina";
        dr2.surname = "Piotrowska";
        dr2.nationality = "Polish";
        dr2.dateOfBirth = LocalDate.of(1991, 2, 25);
        dr2.licenseType = LicenseType.automatic;
        dr2.licenseNumber = 50002;
        dr2.drivingPermit = "PL-C-50002";

        Driver dr3 = new Driver();
        dr3.name = "Lukasz";
        dr3.surname = "Kubiak";
        dr3.nationality = "Polish";
        dr3.dateOfBirth = LocalDate.of(1987, 10, 5);
        dr3.licenseType = LicenseType.manual;
        dr3.licenseNumber = 50003;
        dr3.drivingPermit = "PL-C-50003";

        Driver dr4 = new Driver();
        dr4.name = "Agnieszka";
        dr4.surname = "Szymanska";
        dr4.nationality = "Polish";
        dr4.dateOfBirth = LocalDate.of(1994, 6, 11);
        dr4.licenseType = LicenseType.automatic;
        dr4.licenseNumber = 50004;
        dr4.drivingPermit = "PL-C-50004";

        Driver dr5 = new Driver();
        dr5.name = "Rafal";
        dr5.surname = "Baran";
        dr5.nationality = "Polish";
        dr5.dateOfBirth = LocalDate.of(1982, 3, 30);
        dr5.licenseType = LicenseType.manual;
        dr5.licenseNumber = 50005;
        dr5.drivingPermit = "PL-C-50005";

        session.save(dr1);
        session.save(dr2);
        session.save(dr3);
        session.save(dr4);
        session.save(dr5);

        session.getTransaction().commit();
        session.close();
    }

    public static void main( String[] args )
    {
        //loadFirstTimeData();
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
