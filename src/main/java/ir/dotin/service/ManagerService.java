package ir.dotin.service;

import ir.dotin.entity.Employee;
import ir.dotin.repository.ManagerDao;

import java.util.List;

public class ManagerService {

    public ManagerService() {
    }

    public List<Employee> RegisteredLeaves(Employee manager) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.RegisteredLeaves(manager);
    }

    public void addUser(Employee employee) {
        ManagerDao managerDao = new ManagerDao();
        managerDao.addUser(employee);
    }

    public List<Employee> getAllActiveEmployees() {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.getAllActiveEmployees();
    }

    public void updateUserDetails(Employee employee) {
        ManagerDao managerDao = new ManagerDao();
        managerDao.updateUserDetails(employee);
    }

    public List<Employee> allManager() {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.allManager();
    }

    public Employee getManagerDetail(String firstName, String lastName) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.getManagerDetail(firstName, lastName);
    }

    public void delete(long employeeId) {
        ManagerDao managerDao = new ManagerDao();
        managerDao.delete(employeeId);
    }

    public List<Employee> searchEmployee(Employee employee) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.search(employee);
    }

    public Employee searchId(long id) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchId(id);
    }

    //    public List<String> searchAllUsername(){
//        ManagerDao managerDao = new ManagerDao();
//        return managerDao.searchAllUsername();
//    }
    public Employee searchUsername(String username) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchUsername(username);
    }

    public Employee login(String username, String password) {
        ManagerDao managerDao = new ManagerDao();
        Employee employee = managerDao.login(username, password);
        return employee;
    }

}


