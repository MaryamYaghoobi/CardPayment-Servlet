package ir.dotin.service;

import ir.dotin.entity.Employee;
import ir.dotin.repository.ManagerDao;

import java.util.List;

public class ManagerService {
    public static void addUser(Employee employee) {
        ManagerDao managerDao = new ManagerDao();
        managerDao.addUser(employee);
    }
    public static List<Employee> getAllActiveEmployees(){
        ManagerDao managerDao =new ManagerDao();
        return managerDao.getAllActiveEmployees();
    }
    public static void updateUserDetails(Employee employee){
        ManagerDao managerDao =new ManagerDao();
        managerDao.updateUserDetails(employee);
    }

    public static List<Employee> allManager(){
        ManagerDao managerDao = new ManagerDao();
        return managerDao.allManager();
    }
    public static Employee getManagerDetail(String firstName, String lastName){
        ManagerDao managerDao =new ManagerDao();
        return managerDao.getManagerDetail(firstName,lastName);
    }
//======================================================
    public static void inactiveEmployee(long employeeId){
        ManagerDao managerDao = new ManagerDao();
        managerDao.inactive(employeeId);
    }
    public static List<Employee> searchEmployee(Employee employee){
        ManagerDao managerDao=new ManagerDao();
        return managerDao.search(employee);
    }
//==============================================================
    public static Employee searchId(long id){
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchId(id);
    }
    public List<String> searchAllUsername(){
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchAllUsername();
    }
    public Employee searchUsername(String username){
        ManagerDao managerDao = new ManagerDao();
        return managerDao.searchUsername(username);
    }

}


