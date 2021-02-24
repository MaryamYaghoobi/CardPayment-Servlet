package ir;


import ir.dotin.Person;
import ir.dotin.entity.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


public class MainApp {
    public static void main(String[] args) {
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Person> result = session.createQuery("from Person", Person.class).list();

        result.forEach(person -> {
            System.out.println(person.getFirstName() + " " + person.getLastName() + " id = " + person.getId());
        });

        session.getTransaction().commit();
        session.close();

//        PersonDao p = new PersonDao();
//        System.out.println(p.find());

    }
}