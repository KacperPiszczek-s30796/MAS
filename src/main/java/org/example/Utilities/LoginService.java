package org.example.Utilities;

import org.example.Enums.UserRole;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class LoginService {

    public static boolean login(UserRole role, String name, String surname, String password) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql;

            switch (role) {
                case DOCTOR:
                    hql = "from Doctor d where d.name = :n and d.surname = :s and d.password = :p";
                    break;
                case PATIENT:
                    hql = "from Patient p where p.name = :n and p.surname = :s and p.password = :p";
                    break;
                case PARAMEDIC:
                    hql = "from Paramedic p where p.name = :n and p.surname = :s and p.password = :p";
                    break;
                case DRIVER:
                    hql = "from Driver d where d.name = :n and d.surname = :s and d.password = :p";
                    break;
                default:
                    return false;
            }

            Query<?> q = session.createQuery(hql);
            q.setParameter("n", name);
            q.setParameter("s", surname);
            q.setParameter("p", password);

            return q.uniqueResult() != null;
        }
    }
}
