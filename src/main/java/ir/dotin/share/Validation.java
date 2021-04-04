package ir.dotin.share;

import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.service.EmployeeService;
import ir.dotin.service.ManagerService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Validation {

    public boolean checkUsername(String usernameEntered) {
        boolean invalidUsername = false;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            ManagerService managerService = new ManagerService();
            List<String> allSavedUsernames = managerService.searchAllUsername(session);
            for (String username : allSavedUsernames) {
                if (usernameEntered.equals(username)) {
                    invalidUsername = true;
                    System.out.println("username " + usernameEntered + " is used before.");
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        return invalidUsername;
    }

    public boolean leaveValidation(String leaveFromDate, String leaveToDate, Employee employee) throws ParseException {
        Transaction transaction = null;
        Session session = null;
        boolean leaveValidation = true;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            EmployeeService employeeService = new EmployeeService();
            List<Leaves> leaveEmployeeList = employeeService.EmployeeLeave(employee, session);
            if (leaveEmployeeList != null) {
                for (Leaves leaveEmployee : leaveEmployeeList) {
                    leaveFromDate = leaveEmployee.getLeaveFromDate();
                    leaveToDate = leaveEmployee.getLeaveToDate();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateStart = simpleDateFormat.parse(leaveFromDate);
                    Date dateEnd = simpleDateFormat.parse(leaveToDate);
                    if (dateStart.compareTo(dateEnd) > 0) {
                        System.out.println("leaveFromDate occurs after leaveToDate");
                        leaveValidation = false;
                    }
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        return leaveValidation;
    }
}