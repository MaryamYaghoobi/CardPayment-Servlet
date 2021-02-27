package ir.dotin.service;

import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.repository.EmployeeDao;

import java.util.List;

public class EmployeeService {
   /* public static Employee updateVersion(long employeeId, long lastVersion) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.updateVersion(employeeId,lastVersion);
    }*/

    public static void updateLeaveState(Employee employee, Leaves leaveEmployee){
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.updateLeaveState(employee,leaveEmployee);
    }
    public static List<Leaves> EmployeeLeave(Employee employee){
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.EmployeeLeave(employee);
    }
    public static Employee getUserDetails(long id) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.getUserDetails(id);
    }

    public static Employee searchUsername(String username) {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.searchUsername(username);
    }

    public List<Employee> allEmployee() {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.allEmployee();
    }

    public static void updateUserDetails(Employee employee) {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.updateUserDetails(employee);
    }


}
