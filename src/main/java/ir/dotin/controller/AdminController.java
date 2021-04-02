package ir.dotin.controller;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Employee;
import ir.dotin.service.AdminService;
import ir.dotin.service.EmployeeService;
import ir.dotin.service.SearchCategoryElement;
import ir.dotin.share.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
    public static final String FILE_PATH_PREFIX = "B:";
    public static final String TOMCAT_FILE_PATH = FILE_PATH_PREFIX + "\\apache8\\apache-tomcat-8.0.0\\";
    AdminService adminService = new AdminService();
    EmployeeService employeeService = new EmployeeService();
    SearchCategoryElement searchCategoryElement = new SearchCategoryElement();

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
            case "getAllInActiveEmployees":
                getAllInActiveEmployees(request, response);
                break;
            case "delete":
                delete(request, response);
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
        }
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Employee employee = new Employee();
        String userName = request.getParameter("username");
        Validation usernameValidation = new Validation();
        if (usernameValidation.checkUsername(userName)) {
            request.getSession().setAttribute("invalidationUsername",
                    request.getParameter("username"));
        } else {
            employee.setUsername(userName);
            insertEmployee(request, response);
            long employeeId = employee.getId();
            long lastVersion = employee.getVersion();
            String firstName = request.getParameter("firstName");
            employee.setFirstName(firstName);
            String lastName = request.getParameter("lastName");
            employee.setLastName(lastName);
            String email = request.getParameter("email");
            employee.setEmail(email);
            String fatherName = request.getParameter("fatherName");
            employee.setFatherName(fatherName);
            String dateOfBirth = request.getParameter("dateOfBirth");
            employee.setDateOfBirth(dateOfBirth);
            String password = request.getParameter("password");
            employee.setPassword(password);
            CategoryElement role = searchCategoryElement.findCategoryElement
                    (request.getParameter("role"));
            employee.setRole(role);
            boolean Status = Boolean.valueOf(request.getParameter("disabled"));
            employee.setDisabled(Status);
            String g = request.getParameter("gender");
            CategoryElement gender = searchCategoryElement.findCategoryElement(g);
            employee.setGender(gender);
            String[] managerDetail = request.getParameter("getManagerDetail").split("  ");
            employee.setManager(adminService.getManagerDetail(managerDetail[0], managerDetail[1]));
            employeeService.updateVersion(employeeId, lastVersion);
            adminService.addUser(employee);
            getAllActiveEmployees(request, response);
            String strLastVersion = String.valueOf(lastVersion);
            String strGetVersion = String.valueOf(employee.getVersion());
            if (!strGetVersion.equals(strLastVersion)) {
                System.out.println("Synchronization has occurred");
            }
        }
    }

    public void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("invalidationUsername") != null) {
            request.getSession().removeAttribute("invalidationUsername");
        }
        List<String> allManager = allManager();
        request.setAttribute("managerList", allManager);
        RequestDispatcher rs = request.getRequestDispatcher("insertEmployee.jsp");
        rs.forward(request, response);
    }

    public List<String> allManager() {
        List<String> managerInformation = new ArrayList<>();
        List<Employee> allManager = adminService.allManager();
        for (Employee manager : allManager) {
            String managerName = manager.getFirstName() + "  " + manager.getLastName();
            managerInformation.add(managerName);
        }
        return managerInformation;
    }


    public void getAllActiveEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("AllActiveEmployees", adminService.getAllActiveEmployees());
        RequestDispatcher rs = request.getRequestDispatcher("adminManagement.jsp");
        rs.forward(request, response);
    }

    public void getAllInActiveEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("AllActiveEmployees", adminService.getAllInActiveEmployees());
        RequestDispatcher rs = request.getRequestDispatcher("adminManagement.jsp");
        rs.forward(request, response);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        adminService.delete(employeeId);
        getAllActiveEmployees(request, response);
    }

    public void editAndAppointmentOfManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> allManager = allManager();
        request.setAttribute("managerList", allManager);
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        Employee employee = adminService.searchId(employeeId);
        Employee manager = employee.getManager();
        request.setAttribute("Manager : ", manager.getFirstName() + "  " + manager.getLastName());
        request.setAttribute("Employee : ", employee);
        RequestDispatcher rs = request.getRequestDispatcher("editAndAppointmentOfManager.jsp");
        rs.forward(request, response);
    }

    public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getParameter("id");
        long employeeId = Long.parseLong(s.trim());
        Employee employee = employeeService.getUserDetails(employeeId);
        long employeeIds = employee.getId();
        long lastVersion = employee.getVersion();
        String firstName = request.getParameter("firstName");
        employee.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        employee.setLastName(lastName);
        String email = request.getParameter("email");
        employee.setEmail(email);
        boolean Status = Boolean.parseBoolean(request.getParameter("disabled"));
        employee.setDisabled(Status);
        String[] managerDetail = request.getParameter("getManagerDetail").split("  ");
        employee.setManager(adminService.getManagerDetail(managerDetail[0], managerDetail[1]));
        employeeService.updateVersion(employeeIds, lastVersion);
        adminService.updateUserDetails(employee);
        getAllActiveEmployees(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getVersion());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");
        }
    }

    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Employee employee = new Employee();
        String firstName = request.getParameter("firstName");
        employee.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        employee.setLastName(lastName);
        String username = request.getParameter("username");
        employee.setUsername(username);
        List<Employee> employees = adminService.searchEmployee(employee);
        request.setAttribute("employeeList", employees);
        request.setAttribute("firstName", request.getParameter("firstName"));
        request.setAttribute("lastName", request.getParameter("lastName"));
        request.setAttribute("username", request.getParameter("username"));
        RequestDispatcher rs = request.getRequestDispatcher("adminManagement.jsp");
        rs.forward(request, response);

    }
}
