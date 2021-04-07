package ir.dotin.controller;

import ir.dotin.entity.Employee;
import ir.dotin.service.EmailService;
import ir.dotin.service.EmployeeService;
import ir.dotin.service.LeavesService;
import ir.dotin.service.ManagerService;
import ir.dotin.share.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ManagerController")
public class ManagerController extends HttpServlet {
    public static final String FILE_PATH_PREFIX = "B:";
    public static final String TOMCAT_FILE_PATH = FILE_PATH_PREFIX + "\\apache8\\apache-tomcat-8.0.0\\";
    ManagerService managerService = new ManagerService();
    LeavesService leavesService = new LeavesService();
    EmailService emailService = new EmailService();
    EmployeeService employeeService = new EmployeeService();

    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = null;
        action = request.getParameter("action");
        switch (action) {
            case "search":
                search(request, response);
                break;
            case "getAllActiveEmployees":
                getAllActiveEmployees(request, response);
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
        }
    }


    public void rejectLeave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            long leaveId = Long.parseLong(request.getParameter("leaveId"));
            leavesService.rejectionLeave(leaveId, session);
            System.out.println("The leave request was rejected");
            RegisteredLeaves(request, response);
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

    public void LeaveConfirmation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            long leaveId = Long.parseLong(request.getParameter("leaveId"));
            leavesService.LeaveConfirmation(leaveId, session);
            System.out.println("The leave request was approved");
            RegisteredLeaves(request, response);
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

    public void RegisteredLeaves(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Employee manager = managerService.
                    searchUsername((String) request.getSession().getAttribute("username"), session);
            List<Employee> RegisteredLeaves = managerService.RegisteredLeaves((manager), session);
            request.setAttribute("RegisteredLeaves", RegisteredLeaves);
            RequestDispatcher rs = request.getRequestDispatcher("leavesManagement.jsp");
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


    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Employee employee = new Employee();
            String firstName = request.getParameter("firstName");
            employee.setFirstName(firstName);
            String lastName = request.getParameter("lastName");
            employee.setLastName(lastName);
            String username = request.getParameter("username");
            employee.setUsername(username);
            List<Employee> employees = managerService.searchEmployee(employee, session);
            request.setAttribute("employeeList", employees);
            request.setAttribute("firstName", request.getParameter("firstName"));
            request.setAttribute("lastName", request.getParameter("lastName"));
            request.setAttribute("username", request.getParameter("username"));
            RequestDispatcher rs = request.getRequestDispatcher("employeeManagement.jsp");
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
    public void getAllActiveEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            SessionFactory sessionFactory;
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("META-INF/hibernate.cfg.xml").build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            request.setAttribute("AllActiveEmployees", managerService.getAllActiveEmployees(session));
            RequestDispatcher rs = request.getRequestDispatcher("employeeManagement.jsp");
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

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("username");
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
    }
}
