package ir.dotin.service;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.repository.EmployeeDao;
import org.hibernate.Session;

import java.util.List;

public class EmployeeService {
    public Employee updateVersion(long employeeId, long lastVersion, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.updateVersion(employeeId, lastVersion, session);
    }

    public void updateLeaveState(Employee employee, Leaves leaveEmployee, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.updateLeaveState(employee, leaveEmployee, session);
    }

    public List<Leaves> EmployeeLeave(Employee employee, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.EmployeeLeave(employee, session);
    }

    public Employee getUserDetails(long id, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.getUserDetails(id, session);
    }

    public Employee searchUsername(String username, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.searchUsername(username, session);
    }

    public List<Employee> allEmployee(Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.allEmployee(session);
    }


    public void updateUserDetails(Employee employee, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.updateUserDetails(employee, session);
    }

    public void forwardingMessage(Employee employee, Email email, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.forwardingMessage(employee, email, session);
    }

    public List<Employee> ReceiveMessages(List<Long> employeeIds, Session session) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.ReceiveMessages(employeeIds, session);
    }
}



