package ir.dotin.service;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.repository.EmployeeDao;

import java.util.List;

public class EmployeeService {
    public Employee updateVersion(long employeeId, long lastVersion) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.updateVersion(employeeId, lastVersion);
    }

    public void updateLeaveState(Employee employee, Leaves leaveEmployee) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.updateLeaveState(employee, leaveEmployee);
    }

    public List<Leaves> EmployeeLeave(Employee employee) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.EmployeeLeave(employee);
    }

    public Employee getUserDetails(long id) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.getUserDetails(id);
    }

    public Employee searchUsername(String username) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.searchUsername(username);
    }

    public List<Employee> allEmployee() {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.allEmployee();
    }


    public void updateUserDetails(Employee employee) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.updateUserDetails(employee);
    }

    public void forwardingMessage(Employee employee, Email email) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.forwardingMessage(employee, email);
    }

    public List<Employee> ReceiveMessages(List<Long> employeeIds) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.ReceiveMessages(employeeIds);
    }
}



