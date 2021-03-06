package ir.dotin.repository;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
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
    public Employee updateVersion(long Id, long lastVersion) {
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query querySelect = session.createQuery("select e from Employee e where e.id =:Id");
        querySelect.setParameter("id", Id);
        querySelect.setLockMode(LockModeType.OPTIMISTIC);
        querySelect.getResultList();
        Query queryUpdate = session.createQuery("update Employee e set " +
                "c_version = c_version+1 where e.id =:Id and " +
                "c_version =:c_version  ");
        queryUpdate.setParameter("Id", Id);
        queryUpdate.setParameter("c_version", lastVersion);
        queryUpdate.setLockMode(LockModeType.OPTIMISTIC);
        Employee version = (Employee) queryUpdate.getResultList();
        return version;
    }

    public Employee searchUsername(String username) {
        Employee employeeList = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String strSelectWithUsername = "select e from Employee e " +
                "where e.username =:username";
        Query query = session.createQuery(strSelectWithUsername);
        query.setParameter("username", username);
        employeeList = (Employee) query.getSingleResult();
        return employeeList;
    }

    public void updateUserDetails(Employee employee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        transaction = session.beginTransaction();
        session.update(employee);
        transaction.commit();
        if (transaction != null) {
            transaction.rollback();
        }
    }

    public Employee getUserDetails(long id) {
        Employee employeeList = new Employee();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String getUserDetails = "select e from Employee e " +
        "where e.id =:id";
        Query query = session.createQuery(getUserDetails);
        query.setParameter("id", id);
        employeeList = (Employee) query.getSingleResult();

        return employeeList;
    }

    public List<Employee> allEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String allEmployee = "select e from Employee e";
        Query query = session.createQuery(allEmployee);
        employeeList = query.getResultList();

        return employeeList;
    }

    public void updateLeaveState(Employee employee, Leaves leaveEmployee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        transaction = session.beginTransaction();
        Employee updatedEmployee = session.get(Employee.class, employee.getId());
        updatedEmployee.getLeaveList().add(leaveEmployee);
        session.update(updatedEmployee);
        transaction.commit();
        if (transaction != null) {
            transaction.rollback();
        }
    }

    public List<Leaves> EmployeeLeave(Employee employee) {
        List<Leaves> leaveEmployee = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select e.leaveList from Employee e where e.id =:id");
        query.setParameter("id", employee.getId());
        leaveEmployee = (List<Leaves>) query.getResultList();
        return leaveEmployee;
    }

public void updateSentEmail(Employee employee, Email email) {
    Transaction transaction = null;
    SessionFactory sessionFactory;
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
            ("META-INF/hibernate.cfg.xml").build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    Session session = sessionFactory.openSession();
    session.beginTransaction();
        transaction = session.beginTransaction();
        Employee updatedEmployee = session.get(Employee.class, employee.getId());
        updatedEmployee.getSentEmails().add(email);
        session.update(updatedEmployee);
        transaction.commit();
    }


    public List<Employee> receivedEmailEmployees(List<Long> employeeIds) {
        List<Employee> receivedEmailEmployees = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            MultiIdentifierLoadAccess<Employee> multiLoadAccess =
                    session.byMultipleIds(Employee.class);
            receivedEmailEmployees = multiLoadAccess.multiLoad(employeeIds);


        return receivedEmailEmployees;
    }

}
