package ir.dotin.share;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory factory;

    static {
        SessionFactory sessionFactory;

               StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
               sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        }
      //  factory = sessionFactory.getSessionFactoryBuilder().build();


    public static SessionFactory getSessionFactory() throws HibernateException {
        return factory;

    }

}/*
    SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
*/