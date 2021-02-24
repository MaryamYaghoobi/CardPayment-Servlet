package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
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


    public List<Employee> getAllActiveEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String getAllActiveEmployees = "from Employee  where employeeStatus.code =:code";
            Query query = session.createQuery(getAllActiveEmployees, Employee.class);
            query.setParameter("code", "active");
            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public void addUser(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateUserDetails(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void inactive(long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String getEmployeeId = " from Employee where id =: id";
            Query employeeQuery = session.createQuery(getEmployeeId);
            employeeQuery.setParameter("id", id);
            Employee employee = (Employee) employeeQuery.getSingleResult();
            String getInactive = "from CategoryElement where code =: code";
            Query categoryElementQuery = session.createQuery(getInactive);
            categoryElementQuery.setParameter("code", "inactive");
            CategoryElement categoryElement = (CategoryElement) categoryElementQuery.getSingleResult();
            employee.setEmployeeStatus(categoryElement);
            session.update(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Employee> search(Employee employee) {

        List<Employee> employees = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Employee searchId(long id) {
        Employee employee = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String searchId = "from Employee  where id =: id";
            Query query = session.createQuery(searchId, Employee.class);
            query.setParameter("id", id);
            employee = (Employee) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<String> searchAllUsername() {
        List<String> userUsername = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String searchAllUsername = "select username from Employee";
            Query query = session.createQuery(searchAllUsername, Employee.class);
            userUsername = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userUsername;
    }
    public Employee getManagerDetail(String firstName, String lastName) {
        Employee getManagerDetail = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String ManagerDetails = "from Employee where role.code =: manager and firstName =:firstName and lastName =: lastName";
            Query query = session.createQuery(ManagerDetails, Employee.class);
            query.setParameter("manager", "manager");
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            getManagerDetail = (Employee) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getManagerDetail;
    }

    public Employee searchUsername(String username) {
        Employee employeeList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String searchUsername = "from Employee where username =: username";
            Query query = session.createQuery(searchUsername, Employee.class);
            query.setParameter("username", username);
            employeeList = (Employee) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }
    public List<Employee> allManager() {
        List<Employee> managerEmployeeList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String allManager = "from Employee  where role.code =: manager";
            Query query = session.createQuery(allManager, Employee.class);
            query.setParameter("manager", "manager");
            managerEmployeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return managerEmployeeList;
    }

}




