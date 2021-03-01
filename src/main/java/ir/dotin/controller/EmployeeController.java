package ir.dotin.controller;

import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.service.EmployeeService;
import ir.dotin.service.LeavesService;
import ir.dotin.service.searchCategoryElement;
import ir.dotin.share.LeaveValidation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@WebServlet("/EmployeeController")
public class EmployeeController extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = null;
        action = req.getParameter("action");
        switch (action) {
            case "editEmployee":
                editEmployee(req, resp);
                break;
            case "updateProfile":
                updateProfile(req, resp);
                break;
            case "leaveRequest":
                leaveRequest(req, resp);
                break;
            case "searchLeave":
                searchLeave(req, resp);
                break;

        }
    }

    public void searchLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = EmployeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        List<Leaves> leaveEmployeeList = EmployeeService.EmployeeLeave(employee);
        request.setAttribute("leaveEmployeeList", leaveEmployeeList);
        request.getRequestDispatcher("employeeLeaveStatus.jsp").forward(request, response);
    }


    public void leaveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean validLeaveRequest;
        String leaveFromDate = request.getParameter("leaveFromDate");
        String leaveToDate = request.getParameter("leaveToDate");
        Employee employee = EmployeeService.
                searchUsername((String) request.getSession().getAttribute("username"));

        try {
            validLeaveRequest = LeaveValidation.leaveValidation(leaveFromDate, leaveToDate, employee);
            if (!validLeaveRequest) {
                request.setAttribute("LeaveIsNotValid", "LeaveIsNotValid");
                request.getRequestDispatcher("employeeLeaveRequest.jsp").forward(request, response);
                System.out.println("Leave is not valid");

                return;
            }
            Leaves leaveEmployee = new Leaves(leaveFromDate, leaveToDate,
                    searchCategoryElement.findCategoryElement("register"));
            LeavesService.addLeave(leaveEmployee);
            EmployeeService.updateLeaveState(employee, leaveEmployee);
            request.setAttribute("invalidLeaveRequest", "validLeaveRequest");
            RequestDispatcher rs = request.getRequestDispatcher("employeeLeaveRequest.jsp");
            rs.forward(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Employee register = EmployeeService.getUserDetails(id);
        long employeeId = 1;
        long lastVersion = register.getC_version();
        EmployeeService.updateVersion(employeeId, lastVersion);
        String firstName = request.getParameter("firstName");
        register.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        register.setFirstName(lastName);
        String email = request.getParameter("email");
        register.setEmail(email);
        register.setFatherName(request.getParameter("fatherName"));
              /*  Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
                Matcher matcher = pattern.matcher(email);*/

        // if (!email.isEmpty() && matcher.matches() && !(email == null)) {
        EmployeeService.updateUserDetails(register);
                   /* RequestDispatcher rs = request.getRequestDispatcher("employeeInfo.jsp");
                    rs.forward(request, response);*/
        //    } else {
        //  request.setAttribute("updateError", "Invalid Details");
        //  Employee employee = EmployeeService.getUserDetails(id);
        request.setAttribute("employee", register);
        RequestDispatcher rs = request.getRequestDispatcher("employeeInfo.jsp");
        rs.forward(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(register.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");

        }
    }


    public void editEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Employee employee = EmployeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        request.setAttribute("Employee", employee);
        long employeeId = 1;
        long lastVersion = employee.getC_version();
        EmployeeService.updateVersion(employeeId, lastVersion);
        RequestDispatcher rs = request.getRequestDispatcher("employeeInfo.jsp");
        rs.forward(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");
        }
    }

}
