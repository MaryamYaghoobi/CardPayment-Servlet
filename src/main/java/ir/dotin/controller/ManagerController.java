package ir.dotin.controller;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Employee;
import ir.dotin.service.EmployeeService;
import ir.dotin.service.LeavesService;
import ir.dotin.service.ManagerService;
import ir.dotin.service.searchCategoryElement;
import ir.dotin.share.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/ManagerController")
public class ManagerController extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = null;
        action = request.getParameter("action");
        switch (action) {
            case "addUser":
                addUser(request, response);
                break;
            case "getAllActiveEmployees":
                getAllActiveEmployees(request, response);
                break;
            case "inactive":
                inactive(request, response);
                break;
            case "search":
                search(request, response);
                break;
            case "editAndAppointmentOfManager":
                editAndAppointmentOfManager(request, response);
                break;
            case "updateProfile":
                updateProfile(request, response);
            case "insertEmployee":
                insertEmployee(request, response);
                break;
            case "RegisteredLeaves":
                RegisteredLeaves(request, response);
                break;
            case "LeaveConfirmation":
                LeaveConfirmation(request, response);
                break;
            case "rejectLeave":
                rejectLeave(request, response);
                break;
        }
    }


    public void rejectLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        long leaveId = Long.parseLong(request.getParameter("leaveId"));
        LeavesService.rejectionLeave(leaveId);
        System.out.println("The leave request was rejected");
        rejectLeave(request, response);
    }

    public void LeaveConfirmation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long leaveId = Long.parseLong(request.getParameter("leaveId"));
        LeavesService.LeaveConfirmation(leaveId);
        System.out.println("The leave request was approved");
        RegisteredLeaves(request, response);
    }

    public void RegisteredLeaves(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*Employee manager = ManagerService.
                searchUsername((String) request.getSession().getAttribute("username"));*/
        Employee manager = ManagerService.
                searchUsername((String) request.getSession().getAttribute("username"));
        List<Employee> RegisteredLeaves = ManagerService.RegisteredLeaves((manager));
        request.setAttribute("RegisteredLeaves", RegisteredLeaves);
        RequestDispatcher rs = request.getRequestDispatcher("leaveEmployeesManagement.jsp");
        rs.forward(request, response);
    }


    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        insertEmployee(request, response);
        Employee employee = new Employee();
        long employeeId = 1;
        long lastVersion = employee.getC_version();
        EmployeeService.updateVersion(employeeId, lastVersion);
        String firstName = request.getParameter("firstName");
        employee.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        employee.setFirstName(lastName);
        String email = request.getParameter("email");
        /*Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
        String matcher = String.valueOf(pattern.matcher(email));*/
        employee.setEmail(email);
        String fatherName = request.getParameter("fatherName");
        employee.setFatherName(fatherName);
        //  if (!email.isEmpty() && matcher.matches() && !(email == null))
        String userName = request.getParameter("username");
        Validation usernameValidation = new Validation();
        if (usernameValidation.checkUsername(request.getParameter("username"))) {
            request.getSession().setAttribute("Username Exists", request.getParameter("username"));
        } else {
            employee.setUsername(userName);
        }
        String password = request.getParameter("password");
        employee.setPassword(password);
        String roles = request.getParameter("role");
        CategoryElement role = searchCategoryElement.findCategoryElement(roles);
        employee.setRole(role);
        String Status = request.getParameter("employeeStatus");
        CategoryElement employeeStatus = searchCategoryElement.findCategoryElement(Status);
        employee.setEmployeeStatus(employeeStatus);
        String g = request.getParameter("gender");
        CategoryElement gender = searchCategoryElement.findCategoryElement(g);
        employee.setGender(gender);
        String[] managerDetail = request.getParameter("getManagerDetail").split("  ");
        employee.setManager(ManagerService.getManagerDetail(managerDetail[0], managerDetail[1]));
        ManagerService.addUser(employee);
        getAllActiveEmployees(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");}
    }

    public void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Employee employee=new Employee();
        List<String> allManager = allManager();
        request.setAttribute("managerList", allManager);
        long employeeId = 1;
        long lastVersion = employee.getC_version();
        EmployeeService.updateVersion(employeeId, lastVersion);
        RequestDispatcher rs = request.getRequestDispatcher("insertEmployee.jsp");
        rs.forward(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");}
    }
    public List<String> allManager() {
        List<String> managerInformation = new ArrayList<>();
        List<Employee> allManager = ManagerService.allManager();
        for (Employee manager : allManager) {
            String managerName = manager.getFirstName() + "  " + manager.getLastName();
            managerInformation.add(managerName);
        }
        return managerInformation;
    }
    public void getAllActiveEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("AllActiveEmployees", ManagerService.getAllActiveEmployees());
        RequestDispatcher rs = request.getRequestDispatcher("employeeManagement.jsp");
        rs.forward(request, response);
    }

    public void inactive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        ManagerService.inactiveEmployee(employeeId);
        getAllActiveEmployees(request, response);
    }

    public void editAndAppointmentOfManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Employee> allManager = ManagerService.allManager();
        request.setAttribute("allManager", allManager);
        String id = request.getParameter("employeeId");
        long employeeId = Long.parseLong(id);

        Employee employee = ManagerService.searchId(employeeId);
        long empId = 1;
        long lastVersion = employee.getC_version();
        EmployeeService.updateVersion(empId, lastVersion);
        Employee manager = employee.getManager();
        request.setAttribute("Manager : ", manager.getFirstName() + "  " + manager.getLastName());
        request.setAttribute("Employee : ", employee);
        RequestDispatcher rs = request.getRequestDispatcher("editEmployee.jsp");
       rs.forward(request, response);
       String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");
        }
    }

    public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long employeeId = Long.parseLong(request.getParameter("useId"));
        Employee employee = EmployeeService.getUserDetails(employeeId);
       long empId = 1;
        long lastVersion = employee.getC_version();
        EmployeeService.updateVersion(empId, lastVersion);
        String firstName = request.getParameter("firstName");
        employee.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        employee.setLastName(lastName);
        String email = request.getParameter("email");
       /* Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
        String matcher = String.valueOf(pattern.matcher(email));*/
        employee.setEmail(email);
        String Status = request.getParameter("employeeStatus");
        CategoryElement employeeStatus = searchCategoryElement.findCategoryElement(Status);
        employee.setEmployeeStatus(employeeStatus);
        String[] managerDetail = request.getParameter("getManagerDetail").split("  ");
        employee.setManager(ManagerService.getManagerDetail(managerDetail[0], managerDetail[1]));
        ManagerService.updateUserDetails(employee);
        getAllActiveEmployees(request, response);
      String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");
        }

    }

    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Employee employee = new Employee(request.getParameter("firstName"),
                request.getParameter("lastName")
                , request.getParameter("username"));

        List<Employee> employees = ManagerService.searchEmployee(employee);
        request.setAttribute("employees", employees);
        request.setAttribute("firstName", request.getParameter("firstName"));
        request.setAttribute("lastName", request.getParameter("lastName"));
        request.setAttribute("username", request.getParameter("username"));

        RequestDispatcher rs = request.getRequestDispatcher("employeeManagement.jsp");
        rs.forward(request, response);
    }
}
