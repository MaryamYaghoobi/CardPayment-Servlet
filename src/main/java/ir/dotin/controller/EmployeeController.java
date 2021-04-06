package ir.dotin.controller;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.entity.Leaves;
import ir.dotin.service.EmailService;
import ir.dotin.service.EmployeeService;
import ir.dotin.service.LeavesService;
import ir.dotin.service.SearchCategoryElement;
import ir.dotin.share.HibernateUtil;
import ir.dotin.share.Validation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
            case "cancelLeave":
                cancelLeave(request, response);
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Employee employee = employeeService.
                    searchUsername((String) request.getSession().getAttribute("username"), session);
            List<Object[]> ReceiveMessages = emailService.detailsMessagesReceived(employee, session);
            request.setAttribute("ReceiveMessages", ReceiveMessages);
            RequestDispatcher rs = request.getRequestDispatcher("ReceiveMessages.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void sentMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Employee employee = employeeService.
                    searchUsername((String) request.getSession().getAttribute("username"), session);
            List<Object[]> sentMessages = emailService.detailsMessagesSent(employee, session);
            request.setAttribute("sentMessages", sentMessages);
            RequestDispatcher rs = request.getRequestDispatcher("sentMessages.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void forwardingMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Part part = request.getPart("file");
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                String uploadPath = TOMCAT_FILE_PATH + File.separator + fileName;
                part.write(uploadPath + File.separator);
            }
            Employee senderEmail = employeeService.
                    searchUsername((String) request.getSession().getAttribute("username"), session);
            Email email = new Email();
            String subject = request.getParameter("subject");
            email.setSubject(subject);
            String message = request.getParameter("message");
            email.setContext(message);
            String[] employeeIds = request.getParameterValues("select");
            List<Long> Ids = Arrays.stream(employeeIds)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Employee> ReceiveMessages = employeeService.ReceiveMessages(Ids, session);
            email.setReceiverEmployees(new ArrayList<>());
            email.getReceiverEmployees().addAll(ReceiveMessages);
            emailService.addMessages(email, session);
            employeeService.forwardingMessage(senderEmail, email, session);
            String messages = "forwardingMessage";
            request.setAttribute(messages, true);
            RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }


    public void sendMessages(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<Employee> employeeList = employeeService.allEmployee(session);
            request.setAttribute("employeeList", employeeList);
            RequestDispatcher rs = request.getRequestDispatcher("forwardingMessage.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void searchLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Employee employee = employeeService.
                    searchUsername((String) request.getSession().getAttribute("username"), session);
            List<Leaves> leaveEmployeeList = employeeService.EmployeeLeave(employee, session);
            request.setAttribute("leaveEmployeeList", leaveEmployeeList);
            RequestDispatcher rs = request.getRequestDispatcher("leaveStatus.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void cancelLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            long leaveId = Long.parseLong(request.getParameter("leaveId"));
            leavesService.cancelLeave(leaveId, session);
            System.out.println("The leave request was canceled");
            searchLeave(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void leaveRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Validation validation = new Validation();
            boolean validLeaveRequest;
            String leaveFromDate = request.getParameter("leaveFromDate");
            String leaveToDate = request.getParameter("leaveToDate");
            String leaveFromTime = request.getParameter("leaveFromTime");
            String leaveToTime = request.getParameter("leaveToTime");
            String reason = request.getParameter("reason");
            Employee employee = employeeService.
                    searchUsername((String) request.getSession().getAttribute("username"), session);
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
                    searchCategoryElement.findCategoryElement("register", session));
            leavesService.addLeave(leaveEmployee, session);
            employeeService.updateLeaveState(employee, leaveEmployee, session);
            request.setAttribute("LeaveIsNotValid", "validLeaveRequest");
            RequestDispatcher rs = request.getRequestDispatcher("leaveRequest.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    protected void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            long id = Long.parseLong(request.getParameter("id"));
            Employee employee = employeeService.getUserDetails(id, session);
            String firstName = request.getParameter("firstName");
            employee.setFirstName(firstName);
            String lastName = request.getParameter("lastName");
            employee.setLastName(lastName);
            String email = request.getParameter("email");
            employee.setEmail(email);
            employee.setFatherName(request.getParameter("fatherName"));
            employeeService.updateUserDetails(employee, session);
            request.setAttribute("employee", employee);
            request.setAttribute("succsess", "succsess");
            RequestDispatcher rs = request.getRequestDispatcher("editEmployeeProfiles.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void editEmployeeProfiles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Employee employee = employeeService.
                    searchUsername((String) request.getSession().getAttribute("username"), session);
            request.setAttribute("Employee", employee);
            RequestDispatcher rs = request.getRequestDispatcher("editEmployeeProfiles.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }
}
