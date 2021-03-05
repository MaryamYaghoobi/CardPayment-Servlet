package ir.dotin.share;

import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.repository.ManagerDao;
import ir.dotin.service.EmployeeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Validation {

    public boolean checkUsername(String usernameEntered) {
        ManagerDao managerDao = new ManagerDao();
        int allUsername = managerDao.searchAllUsername(usernameEntered);
        if (allUsername > 0)
            System.out.println("Username Not Exists");
       /*for (String username : allUsername) {
           if (usernameEntered.equals(username)) ;
              *//*  System.out.println("Username Exists");
            } else
                System.out.println("Username Not Exists");*//*
        }*/
        return false;
    }

    public boolean leaveValidation(String leaveFromDate, String leaveToDate, Employee employee) throws ParseException {
        boolean leaveValidation = true;
        EmployeeService employeeService = new EmployeeService();
        List<Leaves> leaveEmployeeList = employeeService.EmployeeLeave(employee);
        if (leaveEmployeeList != null) {
            for (Leaves leaveEmployee : leaveEmployeeList) {
                leaveFromDate = leaveEmployee.getLeaveFromDate();
                leaveToDate = leaveEmployee.getLeaveToDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date dateStart = simpleDateFormat.parse(leaveFromDate);
                Date dateEnd = simpleDateFormat.parse(leaveToDate);
                if (dateStart.compareTo(dateEnd) > 0) {
                    System.out.println("leaveFromDate occurs after leaveToDate");
                    leaveValidation = false;
                }
            }
        }
        return leaveValidation;
    }

}