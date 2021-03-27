package ir.dotin.controller;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.service.*;
import ir.dotin.share.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/ManagerController")
public class ManagerController extends HttpServlet {
    public static final String FILE_PATH_PREFIX = "B:";
    public static final String TOMCAT_FILE_PATH = FILE_PATH_PREFIX + "\\apache8\\apache-tomcat-8.0.0\\";
    ManagerService managerService = new ManagerService();
    LeavesService leavesService = new LeavesService();
    EmailService emailService = new EmailService();
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
            case "RegisteredLeaves":
                RegisteredLeaves(request, response);
                break;
            case "LeaveConfirmation":
                LeaveConfirmation(request, response);
                break;
            case "rejectLeave":
                rejectLeave(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "sendMessages":
                sendMessages(request, response);
                break;
            case "forwardingMessage":
                forwardingMessage(request, response);
                break;
            case "ReceiveMessages":
                ReceiveMessages(request, response);
                break;
            case "downloadAttachment":
                downloadAttachment(request, response);
                break;
        }
    }


    public void rejectLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long leaveId = Long.parseLong(request.getParameter("leaveId"));
        leavesService.rejectionLeave(leaveId);
        System.out.println("The leave request was rejected");
        RegisteredLeaves(request, response);
    }

    public void LeaveConfirmation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long leaveId = Long.parseLong(request.getParameter("leaveId"));
        leavesService.LeaveConfirmation(leaveId);
        System.out.println("The leave request was approved");
        RegisteredLeaves(request, response);
    }

    public void RegisteredLeaves(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Employee manager = managerService.
                searchUsername((String) request.getSession().getAttribute("username"));
        List<Employee> RegisteredLeaves = managerService.RegisteredLeaves((manager));
        request.setAttribute("RegisteredLeaves", RegisteredLeaves);
        RequestDispatcher rs = request.getRequestDispatcher("leavesManagement.jsp");
        rs.forward(request, response);
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
            employee.setManager(managerService.getManagerDetail(managerDetail[0], managerDetail[1]));
            employeeService.updateVersion(employeeId, lastVersion);
            managerService.addUser(employee);
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
        List<Employee> allManager = managerService.allManager();
        for (Employee manager : allManager) {
            String managerName = manager.getFirstName() + "  " + manager.getLastName();
            managerInformation.add(managerName);
        }
        return managerInformation;
    }


    public void getAllActiveEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("AllActiveEmployees", managerService.getAllActiveEmployees());
        RequestDispatcher rs = request.getRequestDispatcher("employeeManagement.jsp");
        rs.forward(request, response);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        managerService.delete(employeeId);
        getAllActiveEmployees(request, response);
    }

    public void editAndAppointmentOfManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> allManager = allManager();
        request.setAttribute("managerList", allManager);
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        Employee employee = managerService.searchId(employeeId);
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
        employee.setManager(managerService.getManagerDetail(managerDetail[0], managerDetail[1]));
        employeeService.updateVersion(employeeIds, lastVersion);
        managerService.updateUserDetails(employee);
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
        List<Employee> employees = managerService.searchEmployee(employee);
        request.setAttribute("employeeList", employees);
        request.setAttribute("firstName", request.getParameter("firstName"));
        request.setAttribute("lastName", request.getParameter("lastName"));
        request.setAttribute("username", request.getParameter("username"));
        RequestDispatcher rs = request.getRequestDispatcher("employeeManagement.jsp");
        rs.forward(request, response);

    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("username");
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
    }

    public void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String fileName = request.getParameter("fileName");
        String filePath = TOMCAT_FILE_PATH;
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i;
        while ((i = fileInputStream.read()) != -1) {
            out.write(i);
        }
        fileInputStream.close();
        out.close();
    }

    public void ReceiveMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = employeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        List<Object[]> ReceiveMessages = emailService.detailsMessagesReceived(employee);
        List<Object[]> sentMessages = emailService.detailsMessagesSent(employee);
        request.setAttribute("ReceiveMessages", ReceiveMessages);
        request.setAttribute("sentMessages", sentMessages);
        RequestDispatcher rs = request.getRequestDispatcher("ReceiveMessages.jsp");
        rs.forward(request, response);

    }

    public void sendMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employeeList = employeeService.allEmployee();
        request.setAttribute("employeeList", employeeList);
        RequestDispatcher rs = request.getRequestDispatcher("forwardingMessage.jsp");
        rs.forward(request, response);
    }

    public void forwardingMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        for (Part part : request.getParts()) {
            part.write(TOMCAT_FILE_PATH + fileName);
        }
        response.getWriter().print("The file uploaded Successfully.");
        Employee senderEmail = employeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        Email email = new Email();
        String subject = request.getParameter("subject");
        email.setSubject(subject);
        String message = request.getParameter("message");
        email.setContext(message);
        String[] employeeIds = request.getParameterValues("select");
        List<Long> Ids = Arrays.stream(employeeIds)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Employee> receivedEmailEmployees = employeeService.ReceiveMessages(Ids);
        email.getReceiverEmployees().addAll(receivedEmailEmployees);
        emailService.addMessages(email);
        employeeService.forwardingMessage(senderEmail, email);
        String messages = "forwardingMessage";
        request.setAttribute(messages, true);
        RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
        rs.forward(request, response);
    }

}
