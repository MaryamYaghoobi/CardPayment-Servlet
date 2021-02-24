package ir.dotin.repository;

import ir.dotin.entity.Employee;
import ir.dotin.share.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public Employee updateVersion(long employeeId,long lastVersion) {
         Session session = HibernateUtil.getSessionFactory().openSession() ;
            Query query = session.createQuery("update employee set name = 'research'," +
                    "lastVersion = version+1 where employee.id =: employeeId and " +
                    "version = lastVersion  ", Employee.class);
            query.setParameter("id", employeeId);
            query.setParameter("version", lastVersion);
            query.setLockMode(LockModeType.OPTIMISTIC);
            Employee version= (Employee) query.getResultList();
        return version;
    }
    public Employee searchUsername(String username) {
        Employee employeeList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String strSelectWithUsername ="from Employee  where username =: username";
            Query query = session.createQuery(strSelectWithUsername, Employee.class);
            query.setParameter("username", username);
            employeeList = (Employee) query.getSingleResult();
        } catch (HibernateException h) {
            h.printStackTrace();
        }
        return employeeList;
    }

    public void updateUserDetails(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        } catch (HibernateException h) {
            if (transaction != null) {
                transaction.rollback();
            }
            h.printStackTrace();
        }
    }
    public Employee getUserDetails(long id) {
        Employee employeeList = new Employee();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String getUserDetails="from Employee  where id =:id";
            Query query = session.createQuery(getUserDetails, Employee.class);
            query.setParameter("id", id);
            employeeList = (Employee) query.getSingleResult();
        } catch (HibernateException h) {
           h.printStackTrace();
        }
        return employeeList;
    }
    public List<Employee> allEmployee() {
        List<Employee> employeeList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String allEmployee="from Employee";
            Query query = session.createQuery(allEmployee, Employee.class);
            employeeList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

}
