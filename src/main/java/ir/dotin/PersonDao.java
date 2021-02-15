package ir.dotin;

import ir.dotin.share.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;

public class PersonDao {
    public Person find() {
        Person p = null;
        Query query;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            query = session.createQuery("select % from person");
        }
        p = (Person) query.getSingleResult();

        return p;
    }

}
