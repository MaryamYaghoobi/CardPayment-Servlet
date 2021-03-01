package ir.dotin.repository;

import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
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
    public Employee updateVersion(long employeeId, long lastVersion) {
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("update employee set " +
                "c_version = c_version+1 where employee.id =:employeeId and " +
                "c_version =:lastVersion  ", Employee.class);
        query.setParameter("employeeId", employeeId);
        query.setParameter("c_version", lastVersion);
        query.setLockMode(LockModeType.OPTIMISTIC);
        Employee version = (Employee) query.getResultList();
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
        String strSelectWithUsername = "from Employee  where username =:username";
        Query query = session.createQuery(strSelectWithUsername, Employee.class);
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
        String getUserDetails = "from Employee  where id =:id";
        Query query = session.createQuery(getUserDetails, Employee.class);
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
        String allEmployee = "from Employee";
        Query query = session.createQuery(allEmployee, Employee.class);
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
        Query query = session.createQuery("select leaveList from Employee where id =: id", Employee.class);
        query.setParameter("id", employee.getId());
        leaveEmployee = (List<Leaves>) query.getResultList();
        return leaveEmployee;
    }
}
