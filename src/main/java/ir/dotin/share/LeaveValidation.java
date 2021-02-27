package ir.dotin.share;

import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.service.EmployeeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class LeaveValidation {
    public static boolean leaveValidation(String leaveFromDate, String leaveToDate, Employee employee) throws ParseException {
        boolean leaveValidation = true;
        List<Leaves> leaveEmployeeList = EmployeeService.EmployeeLeave(employee);
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
