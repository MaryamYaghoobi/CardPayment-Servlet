package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;

public class CategoryElementDao {

    public CategoryElement searchCategoryElement(String code) {
        CategoryElement categoryElementList = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery
                ("select c from CategoryElement c where c.code=:category");
        query.setParameter("category", code);
        categoryElementList = (CategoryElement) query.getSingleResult();
        return categoryElementList;

    }
}



