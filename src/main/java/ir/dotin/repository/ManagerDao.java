package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Employee;
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
        //=========================
        String getAllActiveEmployees = "select e from Employee e where " +
                "e.employeeStatus.code =:code";
        Query query = session.createQuery(getAllActiveEmployees);
        query.setParameter("code", "active");
        employeeList = query.getResultList();
        session.close();
        sessionFactory.close();

        return employeeList;
    }

    //=======================================================
/*public List<Employee> getAllActiveEmployees() {

    List<Employee> employeeList = new ArrayList<>();
    SessionFactory sessionFactory;
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
            ("META-INF/hibernate.cfg.xml").build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    Session session = sessionFactory.openSession();
    //=========================
    String getAllActiveEmployees = "select e from Employee e where " +
            "e.disable =:disable";
    Query query = session.createQuery(getAllActiveEmployees);
    query.setParameter("disable", false);
    employeeList = query.getResultList();
    return employeeList;
}*/
//=======================================================
    public void addUser(Employee employee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(employee);
        transaction.commit();
        session.close();
        sessionFactory.close();

    }

    public void updateUserDetails(Employee employee) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(employee);
        transaction.commit();
        session.close();
        sessionFactory.close();


    }

    public void delete(long id) {
        Transaction transaction = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        String getEmployeeId = " select e from Employee e where e.id =:id";
        Query employeeQuery = session.createQuery(getEmployeeId);
        employeeQuery.setParameter("id", id);
        Employee employee = (Employee) employeeQuery.getSingleResult();
        String getInactive = "select ce from CategoryElement ce where ce.code =:code";
        Query categoryElementQuery = session.createQuery(getInactive);
        categoryElementQuery.setParameter("code", "inactive");
        CategoryElement categoryElement = (CategoryElement) categoryElementQuery.getSingleResult();
        employee.setEmployeeStatus(categoryElement);
        session.update(employee);
        transaction.commit();
        session.close();
        sessionFactory.close();


    }
//=========================================================
/*public void isdeleted(long id) {
    Transaction transaction = null;
    SessionFactory sessionFactory;
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
            ("META-INF/hibernate.cfg.xml").build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    Session session = sessionFactory.openSession();
    transaction = session.beginTransaction();
    String getEmployeeId = " select e from Employee e where e.id =:id";
    Query employeeQuery = session.createQuery(getEmployeeId);
    employeeQuery.setParameter("id", id);
    Employee employee = (Employee) employeeQuery.getSingleResult();
    String getInactive = "UPDATE Employee e SET e.disable = true WHERE e.id =:id";
    Query Inactive = session.createQuery(getInactive);
    Inactive.setParameter("disable", true);
    Employee employeeList = (Employee) Inactive.getSingleResult();
    employee.setDisable(employeeList);
    session.update(employee);
    transaction.commit();

}*/

//=========================================================

    public List<Employee> search(Employee employee) {
        //  Transaction transaction = null;
        List<Employee> employees = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        //  transaction = session.beginTransaction();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> Query = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> emp = Query.from(Employee.class);
            Predicate finalPredicate = criteriaBuilder.conjunction();

            if (employee.getFirstName() != null && !employee.getFirstName().equals("")) {
                finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("firstName"), employee.getFirstName()));
            }
            if (employee.getLastName() != null && !employee.getLastName().equals("")) {
                finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("lastName"), employee.getLastName()));
            }
            if (employee.getUsername() != null && !employee.getUsername().equals("")) {
                finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("username"), employee.getUsername()));
            }
            Query.select(emp).where(finalPredicate);

            org.hibernate.Query<Employee> query = session.createQuery(Query);
            employees = query.getResultList();
        } finally {
            session.close();
            sessionFactory.close();

        }
        return employees;
    }

    public Employee searchId(long id) {
        //Transaction transaction = null;
        Employee employee = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        // session.beginTransaction();
        String searchId = "select e from Employee e where e.id =:id";
        Query query = session.createQuery(searchId);
        query.setParameter("id", id);
        employee = (Employee) query.getSingleResult();
        session.close();
        sessionFactory.close();

        return employee;
    }

    public int searchAllUsername(String username) {
        // Transaction transaction = null;
        Object userUsername = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String searchAllUsername = "select count(e) from Employee e where username =:username";
        Query query = session.createQuery(searchAllUsername, Object.class);
        query.setParameter("username", username);
        userUsername = query.getSingleResult();
        session.close();
        sessionFactory.close();

        return userUsername == null ? 0 : 1;
    }

    public Employee getManagerDetail(String firstName, String lastName) {
        // Transaction transaction = null;
        Employee getManagerDetail = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select e from Employee e where" +
                " e.role.code =:manager and e.firstName =:firstName and e.lastName =:lastName");
        query.setParameter("manager", "manager");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        getManagerDetail = (Employee) query.getSingleResult();
        session.close();
        sessionFactory.close();

        return getManagerDetail;
    }

    public Employee searchUsername(String username) {
        //  Transaction transaction = null;
        Employee employeeList = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select e from Employee e where" +
                " e.username =:username");
        query.setParameter("username", username);
        employeeList = (Employee) query.getSingleResult();
        session.close();
        sessionFactory.close();
        return employeeList;
    }

    public List<Employee> allManager() {
        //  Transaction transaction = null;
        List<Employee> managerEmployeeList = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String allManager = "select e from Employee e where" +
                " e.role.code =:manager";
        Query query = session.createQuery(allManager);
        query.setParameter("manager", "manager");
        managerEmployeeList = query.getResultList();
        session.close();
        sessionFactory.close();

        return managerEmployeeList;
    }

    public List<Employee> RegisteredLeaves(Employee manager) {
        // Transaction transaction = null;

        List<Employee> employeeList = new ArrayList<>();
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String registeredLeaves = "select distinct e from Employee e join fetch e.leaveList el " +
                "where el.leaveStatus.code =:register  and e.role.code =:manager ";
        Query query = session.createQuery(registeredLeaves);
        query.setParameter("manager", manager);
        query.setParameter("register", "register");
        employeeList = query.getResultList();
        session.close();
        sessionFactory.close();

        return employeeList;
    }


    public Employee login(String username, String password) {
        Transaction transaction = null;
        Employee employee = null;
        SessionFactory sessionFactory;
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure
                ("META-INF/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        employee = (Employee) session.createQuery("select e from Employee e where " +
                "e.username =:username and e.password =:password")
                .setParameter("username", username).setParameter("password", password).uniqueResult();
        session.close();
        sessionFactory.close();

        return employee;
    }
}






