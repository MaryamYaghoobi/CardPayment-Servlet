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

    public List<Employee> getAllActiveEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String getAllActiveEmployees = "select e from Employee e where " +
                    "e.disabled =:disabled";
            Query query = session.createQuery(getAllActiveEmployees);
            query.setParameter("disabled", false);
            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }
    public List<Employee> getAllInActiveEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String getAllActiveEmployees = "select e from Employee e where " +
                    "e.disabled =:disabled";
            Query query = session.createQuery(getAllActiveEmployees);
            query.setParameter("disabled", true);
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

    public void delete(long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String getEmployeeId =
                    " select e from Employee e where e.id =:id and e.active =:active";
            Query employeeQuery = session.createQuery(getEmployeeId);
            employeeQuery.setParameter("id", id);
            employeeQuery.setParameter("active", true);
            Employee employee = (Employee) employeeQuery.getSingleResult();
            employee.setActive(true);
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
        Transaction transaction = null;
        List<Employee> employees = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;

    }

    public Employee searchId(long id) {
        Transaction transaction = null;
        Employee employee = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String searchId = "select e from Employee e where e.id =:id";
            Query query = session.createQuery(searchId);
            query.setParameter("id", id);
            employee = (Employee) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    public int searchAllUsername(String username) {
        Object userUsername = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String searchAllUsername = "select count(e) from Employee e where username =:username";
            Query query = session.createQuery(searchAllUsername, Object.class);
            query.setParameter("username", username);
            userUsername = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userUsername == null ? 0 : 1;
    }

    public Employee getManagerDetail(String firstName, String lastName) {
        Employee getManagerDetail = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select e from Employee e where" +
                    " e.role.code =:manager and e.firstName =:firstName and e.lastName =:lastName");
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
            session.beginTransaction();
            Query query = session.createQuery("select e from Employee e where" +
                    " e.username =:username");
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
            session.beginTransaction();
            String allManager = "select e from Employee e where" +
                    " e.role.code =:manager";
            Query query = session.createQuery(allManager);
            query.setParameter("manager", "manager");
            managerEmployeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return managerEmployeeList;
    }

    public List<Employee> RegisteredLeaves(Employee manager) {

        List<Employee> employeeList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String registeredLeaves = "select distinct e from Employee e join fetch e.leaveList el " +
                    "where el.leaveStatus.code =:register  and e.manager.id =:managerId ";
            Query query = session.createQuery(registeredLeaves);
            query.setParameter("managerId", manager.getId());
            query.setParameter("register", "register");
            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}






