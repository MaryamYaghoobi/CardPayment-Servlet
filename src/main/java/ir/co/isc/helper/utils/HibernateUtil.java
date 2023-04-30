package ir.co.isc.helper.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        return sessionFactory;

    }

}