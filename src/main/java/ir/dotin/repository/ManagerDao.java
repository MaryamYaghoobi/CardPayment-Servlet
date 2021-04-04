package ir.dotin.repository;

import ir.dotin.entity.Employee;
import ir.dotin.share.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ManagerDao {

    public List<Employee> getAllActiveEmployees(Session session) {
        List<Employee> employeeList = new ArrayList<>();
        String getAllActiveEmployees = "select e from Employee e where " +
                "e.disabled =:disabled";
        Query query = session.createQuery(getAllActiveEmployees);
        query.setParameter("disabled", false);
        employeeList = query.getResultList();
        return employeeList;
    }

    public List<Employee> search(Employee employee, Session session) {
        List<Employee> employees = new ArrayList<>();
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
        return employees;

    }

    public Employee searchId(long id, Session session) {
        Employee employee = null;
        String searchId = "select e from Employee e where e.id =:id";
        Query query = session.createQuery(searchId);
        query.setParameter("id", id);
        employee = (Employee) query.getSingleResult();
        return employee;
    }


    public Employee getManagerDetail(String firstName, String lastName, Session session) {
        Employee getManagerDetail = null;
        Query query = session.createQuery("select e from Employee e where" +
                " e.role.code =:manager and e.firstName =:firstName and e.lastName =:lastName");
        query.setParameter("manager", "manager");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        getManagerDetail = (Employee) query.getSingleResult();
        return getManagerDetail;
    }

    public Employee searchUsername(String username, Session session) {
        Employee employeeList = null;
        Query query = session.createQuery("select e from Employee e where" +
                " e.username =:username");
        query.setParameter("username", username);
        employeeList = (Employee) query.getSingleResult();
        return employeeList;
    }

    public List<Employee> allManager(Session session) {
        List<Employee> managerEmployeeList = new ArrayList<>();
        String allManager = "select e from Employee e where" +
                " e.role.code =:manager";
        Query query = session.createQuery(allManager);
        query.setParameter("manager", "manager");
        managerEmployeeList = query.getResultList();
        return managerEmployeeList;
    }

    public List<Employee> RegisteredLeaves(Employee manager, Session session) {

        List<Employee> employeeList = new ArrayList<>();
        String registeredLeaves = "select distinct e from Employee e join fetch e.leaveList el " +
                "where el.leaveStatus.code =:register  and e.manager.id =:managerId ";
        Query query = session.createQuery(registeredLeaves);
        query.setParameter("managerId", manager.getId());
        query.setParameter("register", "register");
        employeeList = query.getResultList();
        return employeeList;
    }


    public Employee login(String username, String password) {
        Transaction transaction = null;
        Employee employee = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            employee = (Employee) session.createQuery("select e from Employee e where " +
                    "e.username =:username and e.password =:password")
                    .setParameter("username", username).setParameter("password", password).uniqueResult();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return employee;
    }

    public List<String> searchAllUsername(Session session) {
        List<String> usernames = new ArrayList<>();
        Query query = session.createQuery("select e.username from Employee e ");
        usernames = query.getResultList();
        return usernames;
    }
}






