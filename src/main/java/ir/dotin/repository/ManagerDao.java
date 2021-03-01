package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Employee;
import ir.dotin.share.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ManagerDao {


    public List<Employee> getAllActiveEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            String getAllActiveEmployees = " from Employee  where c_employeeStatus =4";
            Query query = session.createQuery(getAllActiveEmployees, Employee.class);

            employeeList = query.getResultList();

        return employeeList;
    }

    public void addUser(Employee employee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        if (transaction != null) {
            transaction.rollback();
        }


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

    public void inactive(long id) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            transaction = session.beginTransaction();
            String getEmployeeId = " from Employee where id =:id";
            Query employeeQuery = session.createQuery(getEmployeeId);
            employeeQuery.setParameter("id", id);
            Employee employee = (Employee) employeeQuery.getSingleResult();
            String getInactive = "from CategoryElement where name =:name";
            Query categoryElementQuery = session.createQuery(getInactive);
            categoryElementQuery.setParameter("name", "inactive");
            CategoryElement categoryElement = (CategoryElement) categoryElementQuery.getSingleResult();
            employee.setEmployeeStatus(categoryElement);
            session.update(employee);
            transaction.commit();
        if (transaction != null) {
            transaction.rollback();
        }
    }

    public List<Employee> search(Employee employee) {

        List<Employee> employees = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
//        session.beginTransaction();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> Query = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> emp = Query.from(Employee.class);

            Predicate report = criteriaBuilder.conjunction();

            if (employee.getFirstName() != null && !employee.getFirstName().equals("")) {
                report = criteriaBuilder.and(report, criteriaBuilder.equal(emp.get("firstName"), employee.getFirstName()));
            }
            if (employee.getLastName() != null && !employee.getLastName().equals("")) {
                report = criteriaBuilder.and(report, criteriaBuilder.equal(emp.get("lastName"), employee.getLastName()));
            }
            if (employee.getUsername() != null && !employee.getUsername().equals("")) {
                report = criteriaBuilder.and(report, criteriaBuilder.equal(emp.get("username"), employee.getUsername()));
            }
            Query.select(emp).where(report);

            org.hibernate.Query<Employee> query = session.createQuery(Query);
            employees = query.getResultList();
        } finally {
            session.close();
        }
        return employees;
    }

    public Employee searchId(long id) {
        Employee employee = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            String searchId = "from Employee  where id =:id";
            Query query = session.createQuery(searchId, Employee.class);
            query.setParameter("id", id);
            employee = (Employee) query.getSingleResult();
        return employee;
    }

    public List<String> searchAllUsername() {
        List<String> userUsername = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            String searchAllUsername = "select username from Employee";
            Query query = session.createQuery(searchAllUsername, Employee.class);
            userUsername = query.getResultList();
        return userUsername;
    }
    public Employee getManagerDetail(String firstName, String lastName) {
        Employee getManagerDetail = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            String ManagerDetails = "from Employee where c_manager =1 and firstName =:firstName and lastName =: lastName";
            Query query = session.createQuery(ManagerDetails, Employee.class);

            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            getManagerDetail = (Employee) query.getSingleResult();
        return getManagerDetail;
    }

    public Employee searchUsername(String username) {
        Employee employeeList = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
       // session.beginTransaction();
        Query query = session.createQuery("from Employee  where" +
                " Employee.username =:username");
        query.setParameter("username", username);
        employeeList = (Employee) query.getSingleResult();
        return employeeList;
}

    public List<Employee> allManager() {
        List<Employee> managerEmployeeList = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            String allManager = "from Employee  where c_manager =1";
            Query query = session.createQuery(allManager, Employee.class);
           // query.setParameter("manager", "manager");
            managerEmployeeList = query.getResultList();

        return managerEmployeeList;
    }
    public List<Employee> RegisteredLeaves(Employee manager) {
        List<Employee> employeeList = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
            Query query = session.createQuery
                    ("select distinct emp from Employee emp join fetch emp.leaveList  " +
                            "where emp.leaveList.leaveStatus.code =:register  and c_manager =1 ");

            query.setParameter("register", "register");
            employeeList = query.getResultList();

        return employeeList;
    }
}




