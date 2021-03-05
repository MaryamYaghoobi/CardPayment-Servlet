package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Leaves;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;

public class LeavesDao {
    public static void addLeave(Leaves leaveEmployee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        transaction = session.beginTransaction();
        session.save(leaveEmployee);
        transaction.commit();
        if (transaction != null) {
            transaction.rollback();
        }
    }

    public void LeaveConfirmation(long leaveId) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        transaction = session.beginTransaction();
        Query categoryElementQuery = session.createQuery
                ("select ce from CategoryElement ce where ce.code =:code");
        categoryElementQuery.setParameter("code", "accept");
        CategoryElement accept = (CategoryElement) categoryElementQuery.getSingleResult();
        Query query = session.createQuery
                ("update Leaves l set l.leaveStatus =:accept " +
                        "  where l.id =:id");
        query.setParameter("accept", accept);
        query.setParameter("id", leaveId);
        query.executeUpdate();
        transaction.commit();

        if (transaction != null) {
            transaction.rollback();
        }
    }

    public void rejectionLeave(long leaveId) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        transaction = session.beginTransaction();
        Query categoryElementQuery = session.createQuery
                (" select ce from CategoryElement ce where ce.code =:code");
        categoryElementQuery.setParameter("code", "reject");
        CategoryElement reject = (CategoryElement) categoryElementQuery.getSingleResult();
        Query query = session.createQuery
                ("update Leaves l set l.leaveStatus =:rejected " +
                        " where l.id =:id");
        query.setParameter("reject", reject);
        query.setParameter("id", leaveId);
        query.executeUpdate();
        transaction.commit();
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
