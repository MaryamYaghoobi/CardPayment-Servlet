package ir.dotin.share;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory factory;

    static {
        Metadata meta;
        try (StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().
                configure("META-INF/hibernate.cfg.xml").build()) {
            meta = new MetadataSources(ssr).getMetadataBuilder().build();
        }
        factory = meta.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        return factory;

    }

}
