package org.example.Utilities;

import org.example.Entities.Patient;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PatientRepository {
    public static List<Patient> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Patient> list = session
                .createQuery("from Patient", Patient.class)
                .list();
        session.close();
        return list;
    }
    public static List<Patient> findAllWithAppointments() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            List<Patient> patients = session
                    .createQuery("from Patient", Patient.class)
                    .list();

            for (Patient p : patients) {
                Hibernate.initialize(p.getAppointments());
            }

            tx.commit();
            return patients;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
