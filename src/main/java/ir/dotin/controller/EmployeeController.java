package ir.dotin.controller;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.service.EmailService;
import ir.dotin.service.EmployeeService;
import ir.dotin.service.LeavesService;
import ir.dotin.service.SearchCategoryElement;
import ir.dotin.share.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet("/EmployeeController")
public class EmployeeController extends HttpServlet {
    public static final String FILE_PATH_PREFIX = "B:";
    public static final String TOMCAT_FILE_PATH = FILE_PATH_PREFIX
            + "\\javaSchool\\SESSION11\\apache-tomcat-8.0.0-RC5\\apache-tomcat-8.0.0-RC5";

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
            case "editEmployeeProfiles":
                editEmployeeProfiles(request, response);
                break;
            case "updateProfile":
                updateProfile(request, response);
                break;
            case "leaveRequest":
                leaveRequest(request, response);
                break;
            case "searchLeave":
                searchLeave(request, response);
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
            case "sentMessages":
                sentMessages(request, response);
                break;
            case "cancelLeave":
                cancelLeave(request, response);
                break;

        }
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
        request.setAttribute("ReceiveMessages", ReceiveMessages);
        RequestDispatcher rs = request.getRequestDispatcher("ReceiveMessages.jsp");
        rs.forward(request, response);
    }

    public void sentMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = employeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        List<Object[]> sentMessages = emailService.detailsMessagesSent(employee);
        request.setAttribute("sentMessages", sentMessages);
        RequestDispatcher rs = request.getRequestDispatcher("sentMessages.jsp");
        rs.forward(request, response);
    }

    public void forwardingMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part part = request.getPart("file");
        String fileName = part.getSubmittedFileName();
        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = TOMCAT_FILE_PATH + File.separator + fileName;
            part.write(uploadPath + File.separator);
        }
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
        List<Employee> ReceiveMessages = employeeService.ReceiveMessages(Ids);
        email.setReceiverEmployees(new ArrayList<>());
        email.getReceiverEmployees().addAll(ReceiveMessages);
        emailService.addMessages(email);
        employeeService.forwardingMessage(senderEmail, email);
        String messages = "forwardingMessage";
        request.setAttribute(messages, true);
        RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
        rs.forward(request, response);
    }


    public void sendMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employeeList = employeeService.allEmployee();
        request.setAttribute("employeeList", employeeList);
        RequestDispatcher rs = request.getRequestDispatcher("forwardingMessage.jsp");
        rs.forward(request, response);
    }

    public void searchLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = employeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        List<Leaves> leaveEmployeeList = employeeService.EmployeeLeave(employee);
        request.setAttribute("leaveEmployeeList", leaveEmployeeList);
        RequestDispatcher rs = request.getRequestDispatcher("leaveStatus.jsp");
        rs.forward(request, response);
    }

    public void cancelLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long leaveId = Long.parseLong(request.getParameter("leaveId"));
        leavesService.cancelLeave(leaveId);
        System.out.println("The leave request was canceled");
        searchLeave(request, response);
    }

    public void leaveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Validation validation = new Validation();
        boolean validLeaveRequest;
        String leaveFromDate = request.getParameter("leaveFromDate");
        String leaveToDate = request.getParameter("leaveToDate");
        String leaveFromTime = request.getParameter("leaveFromTime");
        String leaveToTime = request.getParameter("leaveToTime");
        String reason = request.getParameter("reason");
        Employee employee = employeeService.
                searchUsername((String) request.getSession().getAttribute("username"));

        try {
            validLeaveRequest = validation.leaveValidation
                    (leaveFromDate, leaveToDate, employee);
            if (!validLeaveRequest) {
                request.setAttribute("LeaveIsNotValid", "LeaveIsNotValid");
                RequestDispatcher rs = request.getRequestDispatcher("leaveRequest.jsp");
                rs.forward(request, response);
                System.out.println("Leave is not valid");
                return;
            }
            Leaves leaveEmployee = new Leaves(leaveFromDate, leaveToDate, leaveFromTime, leaveToTime, reason,
                    searchCategoryElement.findCategoryElement("register"));
            leavesService.addLeave(leaveEmployee);
            employeeService.updateLeaveState(employee, leaveEmployee);
            request.setAttribute("invalidLeaveRequest", "validLeaveRequest");
            RequestDispatcher rs = request.getRequestDispatcher("leaveRequest.jsp");
            rs.forward(request, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Employee employee = employeeService.getUserDetails(id);
        String firstName = request.getParameter("firstName");
        employee.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        employee.setLastName(lastName);
        String email = request.getParameter("email");
        employee.setEmail(email);
        employee.setFatherName(request.getParameter("fatherName"));
        employeeService.updateUserDetails(employee);
        request.setAttribute("employee", employee);
        RequestDispatcher rs = request.getRequestDispatcher("editEmployeeProfiles.jsp");
        rs.forward(request, response);
    }

    public void editEmployeeProfiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee = employeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        request.setAttribute("Employee", employee);
        RequestDispatcher rs = request.getRequestDispatcher("editEmployeeProfiles.jsp");
        rs.forward(request, response);
    }
}
