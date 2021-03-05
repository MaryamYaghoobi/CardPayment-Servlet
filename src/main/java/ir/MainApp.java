package ir;


import ir.dotin.entity.CategoryElement;
import ir.dotin.service.SearchCategoryElement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.util.List;


public class MainApp {
    public static void main(String[] args) {


            SessionFactory sessionFactory;
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                    ("META-INF/hibernate.cfg.xml").build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

          //  return categoryElement;
        }
    }
//-----------------------
         /*SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();



        session.getTransaction().commit();
        session.close();

    }*/

