package ir.dotin.dao;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory factory;

    static {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        factory = meta.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        return factory;

    }

}
