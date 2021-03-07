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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
    SearchCategoryElement searchCategoryElement=new SearchCategoryElement();

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
        List<Object[]> sentMessages = emailService.detailsMessagesSent(employee);
        request.setAttribute("ReceiveMessages", ReceiveMessages);
        request.setAttribute("sentMessages", sentMessages);
        RequestDispatcher rs = request.getRequestDispatcher("ReceiveMessages.jsp");
        rs.forward(request, response);

    }

    public void forwardingMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        for (Part part : request.getParts()) {
            part.write(TOMCAT_FILE_PATH + fileName); }
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
        List<Employee> receivedEmailEmployees = employeeService.receivedEmailEmployees(Ids);
        email.getReceiverEmployees().addAll(receivedEmailEmployees);
        emailService.addMessages(email);
        employeeService.updateSentEmailEmployee(senderEmail, email);
        String messages="forwardingMessage";
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


    public void leaveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Validation validation =new Validation();
        boolean validLeaveRequest;
        String leaveFromDate = request.getParameter("leaveFromDate");
        String leaveToDate = request.getParameter("leaveToDate");
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
            Leaves leaveEmployee = new Leaves(leaveFromDate, leaveToDate,reason,
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
        Employee register = employeeService.getUserDetails(id);
       /* long employeeId = 1;
        long lastVersion = register.getC_version();
        employeeService.updateVersion(employeeId, lastVersion);*/
        String firstName = request.getParameter("firstName");
        register.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        register.setLastName(lastName);
        String email = request.getParameter("email");
        register.setEmail(email);
        register.setFatherName(request.getParameter("fatherName"));
              /*  Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
                Matcher matcher = pattern.matcher(email);*/

        // if (!email.isEmpty() && matcher.matches() && !(email == null)) {
        employeeService.updateUserDetails(register);
        //    } else {
        //  request.setAttribute("updateError", "Invalid Details");
        //  Employee employee = EmployeeService.getUserDetails(id);
        request.setAttribute("employee", register);
        RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
        rs.forward(request, response);
       /* String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(register.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");
        }*/
    }
    public void editEmployeeProfiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Employee employee = employeeService.
                searchUsername((String) request.getSession().getAttribute("username"));
        request.setAttribute("Employee", employee);
        long employeeId = 1;
        long lastVersion = employee.getC_version();
        employeeService.updateVersion(employeeId, lastVersion);
        RequestDispatcher rs = request.getRequestDispatcher("editEmployeeProfiles.jsp");
        rs.forward(request, response);
        String strLastVersion = String.valueOf(lastVersion);
        String strGetVersion = String.valueOf(employee.getC_version());
        if (!strGetVersion.equals(strLastVersion)) {
            System.out.println("Synchronization has occurred");
        }
    }

}
