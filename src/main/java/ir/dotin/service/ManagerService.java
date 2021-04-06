package ir.dotin.service;

import ir.dotin.entity.Employee;
import ir.dotin.repository.ManagerDao;
import org.hibernate.Session;

import java.util.List;

public class ManagerService {
    public List<Employee> RegisteredLeaves(Employee manager, Session session) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.RegisteredLeaves(manager, session);
    }

    public List<Employee> allManager(Session session) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.allManager(session);
    }

    public List<Employee> searchEmployee(Employee employee, Session session) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.search(employee, session);
    }

    public Employee searchId(long id, Session session) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchId(id, session);
    }

    public Employee searchUsername(String username, Session session) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchUsername(username, session);
    }

    public Employee login(String username, String password) {
        ManagerDao managerDao = new ManagerDao();
        Employee employee = managerDao.login(username, password);
        return employee;
    }

    public List<String> searchAllUsername(Session session) {
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchAllUsername(session);
    }

}


