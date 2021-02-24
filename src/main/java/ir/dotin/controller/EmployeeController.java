package ir.dotin.controller;

import ir.dotin.entity.Employee;
import ir.dotin.service.EmployeeService;

import javax.persistence.OptimisticLockException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        }
    }

    protected void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     /*   HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {*/
        //long id = (long) session.getAttribute("userId");
        long id = Long.parseLong(request.getParameter("id"));
        Employee register = EmployeeService.getUserDetails(id);
        long employeeId = 1;
        long lastVersion = register.getVersion();
        EmployeeService.updateVersion(employeeId, lastVersion);
        String firstName = request.getParameter("firstName");
        register.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        register.setFirstName(lastName);
        String email = request.getParameter("email");

        register.setFatherName(request.getParameter("fatherName"));
              /*  Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
                Matcher matcher = pattern.matcher(email);*/
        register.setEmail(email);
        // if (!email.isEmpty() && matcher.matches() && !(email == null)) {
        EmployeeService.updateUserDetails(register);
                   /* RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
                    rs.forward(request, response);*/
        //    } else {
        //  request.setAttribute("updateError", "Invalid Details");
        //  Employee employee = EmployeeService.getUserDetails(id);
        request.setAttribute("employee", register);
        RequestDispatcher rs = request.getRequestDispatcher("employeeInfo.jsp");
        rs.forward(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(register.getVersion());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");

        }
    }


    public void editEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Employee employee = EmployeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        request.setAttribute("Employee", employee);
        long employeeId = 1;
        long lastVersion = employee.getVersion();
        EmployeeService.updateVersion(employeeId, lastVersion);
        RequestDispatcher rs = request.getRequestDispatcher("employeeInfo.jsp");
        rs.forward(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getVersion());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");
        }

    }
}
