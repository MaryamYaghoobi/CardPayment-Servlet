package ir.dotin.service;

import ir.dotin.entity.Employee;
import ir.dotin.repository.AdminDao;

import java.util.List;

public class AdminService {
    public void addUser(Employee employee) {
        AdminDao adminDao = new AdminDao();
        adminDao.addUser(employee);
    }

    public List<Employee> getAllActiveEmployees() {
        AdminDao adminDao = new AdminDao();
        return adminDao.getAllActiveEmployees();
    }

    public List<Employee> getAllInActiveEmployees() {
        AdminDao adminDao = new AdminDao();
        return adminDao.getAllInActiveEmployees();
    }

    public void updateUserDetails(Employee employee) {
        AdminDao adminDao = new AdminDao();
        adminDao.updateUserDetails(employee);
    }

    public List<Employee> allManager() {
        AdminDao adminDao = new AdminDao();
        return adminDao.allManager();
    }

    public Employee getManagerDetail(String firstName, String lastName) {
        AdminDao adminDao = new AdminDao();
        return adminDao.getManagerDetail(firstName, lastName);
    }

    public void delete(long employeeId) {
        AdminDao adminDao = new AdminDao();
        adminDao.delete(employeeId);
    }

    public List<Employee> searchEmployee(Employee employee) {
        AdminDao adminDao = new AdminDao();
        return adminDao.search(employee);
    }

    public Employee searchId(long id) {
        AdminDao adminDao = new AdminDao();
        return adminDao.searchId(id);
    }

    public int searchAllUsername(String username) {
        AdminDao adminDao = new AdminDao();
        return adminDao.searchAllUsername(username);
    }

    public Employee searchUsername(String username) {
        AdminDao adminDao = new AdminDao();
        return adminDao.searchUsername(username);
    }
}
