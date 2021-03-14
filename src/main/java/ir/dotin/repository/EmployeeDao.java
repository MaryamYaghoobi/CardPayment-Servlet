package ir.dotin.repository;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.share.HibernateUtil;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public Employee updateVersion(long id, long c_version) {
        Transaction transaction = null;
        Employee version = null;
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Query querySelect = session.createQuery("select e from Employee e where e.id =:id");
        querySelect.setParameter("id", id);
        querySelect.setLockMode(LockModeType.OPTIMISTIC);
        querySelect.getResultList();
        Query queryUpdate = session.createQuery("update Employee e set " +
                "version = version+1 where e.id =:id  ");

        queryUpdate.setParameter("id", id);
        queryUpdate.setParameter("version", c_version);
        // queryUpdate.setLockMode(LockModeType.OPTIMISTIC);
        version = (Employee) queryUpdate.getResultList();
        transaction.commit();
        return version;
    }

    public Employee searchUsername(String username) {
        Transaction transaction = null;
        Employee employeeList = null;
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            String strSelectWithUsername = "select e from Employee e " +
                    "where e.username =:username";
            Query query = session.createQuery(strSelectWithUsername);
            query.setParameter("username", username);
            employeeList = (Employee) query.getSingleResult();
            transaction.commit();
       session.close();
        return employeeList;
    }

    public void updateUserDetails(Employee employee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();

            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        session.close();
        }


    public Employee getUserDetails(long id) {
        Employee employeeList = new Employee();
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            String getUserDetails = "select e from Employee e " +
                    "where e.id =:id";
            Query query = session.createQuery(getUserDetails);
            query.setParameter("id", id);
            employeeList = (Employee) query.getSingleResult();
session.close();
        return employeeList;
    }

    public List<Employee> allEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            String allEmployee = "select e from Employee e";
            Query query = session.createQuery(allEmployee);
            employeeList = query.getResultList();
            session.close();
        return employeeList;
    }

    public void updateLeaveState(Employee employee, Leaves leaveEmployee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();

            transaction = session.beginTransaction();
            Employee updatedEmployee = session.get(Employee.class, employee.getId());
            updatedEmployee.getLeaveList().add(leaveEmployee);
            session.update(updatedEmployee);
            transaction.commit();
       session.close();

    }

    public List<Leaves> EmployeeLeave(Employee employee) {
        Transaction transaction = null;
        List<Leaves> leaveEmployee = new ArrayList<>();
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        transaction=  session.beginTransaction();
            Query query = session.createQuery("select e.leaveList from Employee e" +
                    " where e.id =:id");
            query.setParameter("id", employee.getId());
            leaveEmployee = (List<Leaves>) query.getResultList();
            transaction.commit();
            session.close();
        return leaveEmployee;
    }

    public void forwardingMessage(Employee employee, Email email) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Employee updatedEmployee = session.get(Employee.class, employee.getId());
            updatedEmployee.getSentEmails().add(email);
            session.update(updatedEmployee);
            transaction.commit();

    }


    public List<Employee> ReceiveMessages(List<Long> employeeIds) {
        List<Employee> receivedEmailEmployees = new ArrayList<>();
        SessionFactory sessionFactory;
        // configures settings from hibernate.cfg.xml
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            MultiIdentifierLoadAccess<Employee> multiLoadAccess =
                    session.byMultipleIds(Employee.class);
            receivedEmailEmployees = multiLoadAccess.multiLoad(employeeIds);
        return receivedEmailEmployees;
    }
}
