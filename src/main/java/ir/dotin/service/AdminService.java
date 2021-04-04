package ir.dotin.service;

import ir.dotin.entity.Employee;
import ir.dotin.repository.AdminDao;
import org.hibernate.Session;

import java.util.List;

public class AdminService {
    public void addUser(Employee employee, Session session) {
        AdminDao adminDao = new AdminDao();
        adminDao.addUser(employee, session);
    }

    public List<Employee> getAllActiveEmployees(Session session) {
        AdminDao adminDao = new AdminDao();
        return adminDao.getAllActiveEmployees(session);
    }

    public void updateUserDetails(Employee employee, Session session) {
        AdminDao adminDao = new AdminDao();
        adminDao.updateUserDetails(employee, session);
    }

    public List<Employee> allManager(Session session) {
        AdminDao adminDao = new AdminDao();
        return adminDao.allManager(session);
    }

    public Employee getManagerDetail(String firstName, String lastName, Session session) {
        AdminDao adminDao = new AdminDao();
        return adminDao.getManagerDetail(firstName, lastName, session);
    }

    public void delete(Employee employee, Session session) {
        AdminDao adminDao = new AdminDao();
        adminDao.delete(employee, session);
    }

    public void active(Employee employee, Session session) {
        AdminDao adminDao = new AdminDao();
        adminDao.active(employee, session);
    }

    public void inActive(Employee employee, Session session) {
        AdminDao adminDao = new AdminDao();
        adminDao.inActive(employee, session);
    }

    public List<Employee> searchEmployee(Employee employee, Session session) {
        AdminDao adminDao = new AdminDao();
        return adminDao.search(employee, session);
    }

    public Employee searchId(long id, Session session) {
        AdminDao adminDao = new AdminDao();
        return adminDao.searchId(id, session);
    }


    public List<String> searchAllUsername(String username, Session session) {
        AdminDao adminDao = new AdminDao();
        return adminDao.searchAllUsername(session);
    }

    public Employee searchUsername(String username, Session session) {
        AdminDao adminDao = new AdminDao();
        return adminDao.searchUsername(username, session);
    }
}
