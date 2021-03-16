package ir.dotin.repository;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.share.HibernateUtil;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public Employee updateVersion(long id, long version) {
        Transaction transaction = null;
        Employee employee = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query querySelect = session.createQuery("select e from Employee e where e.id =:id");
            querySelect.setParameter("id", id);
            querySelect.setLockMode(LockModeType.OPTIMISTIC);
            querySelect.getResultList();
            Query queryUpdate = session.createQuery("update Employee e set " +
                    "version = version+1 where e.id =:id and version=:version ");
            queryUpdate.setParameter("id", id);
            queryUpdate.setParameter("version", version);
            employee = (Employee) queryUpdate.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    public Employee searchUsername(String username) {
        Transaction transaction = null;
        Employee employeeList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String strSelectWithUsername = "select e from Employee e " +
                    "where e.username =:username";
            Query query = session.createQuery(strSelectWithUsername);
            query.setParameter("username", username);
            employeeList = (Employee) query.getSingleResult();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
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
        }
    }

    public Employee getUserDetails(long id) {
        Employee employeeList = new Employee();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String getUserDetails = "select e from Employee e " +
                    "where e.id =:id";
            Query query = session.createQuery(getUserDetails);
            query.setParameter("id", id);
            employeeList = (Employee) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;

    }

    public List<Employee> allEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String allEmployee = "select e from Employee e";
            Query query = session.createQuery(allEmployee);
            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public void updateLeaveState(Employee employee, Leaves leaveEmployee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee updatedEmployee = session.get(Employee.class, employee.getId());
            updatedEmployee.getLeaveList().add(leaveEmployee);
            session.update(updatedEmployee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    public List<Leaves> EmployeeLeave(Employee employee) {
        Transaction transaction = null;
        List<Leaves> leaveEmployee = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("select e.leaveList from Employee e" +
                    " where e.id =:id");
            query.setParameter("id", employee.getId());
            leaveEmployee = (List<Leaves>) query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaveEmployee;
    }

    public void forwardingMessage(Employee employee, Email email) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee updatedEmployee = session.get(Employee.class, employee.getId());
            updatedEmployee.getSentEmails().add(email);
            session.update(updatedEmployee);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<Employee> ReceiveMessages(List<Long> employeeIds) {
        List<Employee> receivedEmailEmployees = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            MultiIdentifierLoadAccess<Employee> multiLoadAccess =
                    session.byMultipleIds(Employee.class);
            receivedEmailEmployees = multiLoadAccess.multiLoad(employeeIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receivedEmailEmployees;
    }
}
