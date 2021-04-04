package ir.dotin.repository;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    public Employee updateVersion(long id, long version, Session session) {
        Employee employee = null;
        Query querySelect = session.createQuery("select e from Employee e where e.id =:id");
        querySelect.setParameter("id", id);
        querySelect.setLockMode(LockModeType.OPTIMISTIC);
        querySelect.getResultList();
        Query queryUpdate = session.createQuery("update Employee e set " +
                "e.version = e.version+1 where e.id =:id and e.version=:version ");
        queryUpdate.setParameter("id", id);
        queryUpdate.setParameter("version", version);
        employee = (Employee) queryUpdate.getResultList();
        return employee;
    }

    public Employee searchUsername(String username, Session session) {
        Employee employeeList = null;
        String strSelectWithUsername = "select e from Employee e " +
                "where e.username =:username";
        Query query = session.createQuery(strSelectWithUsername);
        query.setParameter("username", username);
        employeeList = (Employee) query.getSingleResult();
        return employeeList;
    }

    public void updateUserDetails(Employee employee, Session session) {
        session.update(employee);
    }


    public Employee getUserDetails(long id, Session session) {
        Employee employeeList = new Employee();
        String getUserDetails = "select e from Employee e " +
                "where e.id =:id";
        Query query = session.createQuery(getUserDetails);
        query.setParameter("id", id);
        employeeList = (Employee) query.getSingleResult();
        return employeeList;

    }

    public List<Employee> allEmployee(Session session) {
        List<Employee> employeeList = new ArrayList<>();
        String allEmployee = "select e from Employee e";
        Query query = session.createQuery(allEmployee);
        employeeList = query.getResultList();
        return employeeList;
    }

    public void updateLeaveState(Employee employee, Leaves leaveEmployee, Session session) {
        Employee updatedEmployee = session.get(Employee.class, employee.getId());
        updatedEmployee.getLeaveList().add(leaveEmployee);
        session.update(updatedEmployee);
    }


    public List<Leaves> EmployeeLeave(Employee employee, Session session) {
        List<Leaves> leaveEmployee = new ArrayList<>();
        Query query = session.createQuery("select e.leaveList from Employee e" +
                " where e.id =:id");
        query.setParameter("id", employee.getId());
        leaveEmployee = (List<Leaves>) query.getResultList();
        return leaveEmployee;
    }

    public void forwardingMessage(Employee employee, Email email, Session session) {
        Employee updatedEmployee = session.get(Employee.class, employee.getId());
        updatedEmployee.getSentEmails().add(email);
        session.update(updatedEmployee);
    }


    public List<Employee> ReceiveMessages(List<Long> employeeIds, Session session) {
        List<Employee> receivedEmailEmployees = new ArrayList<>();
        MultiIdentifierLoadAccess<Employee> multiLoadAccess =
                session.byMultipleIds(Employee.class);
        receivedEmailEmployees = multiLoadAccess.multiLoad(employeeIds);
        return receivedEmailEmployees;
    }
}
