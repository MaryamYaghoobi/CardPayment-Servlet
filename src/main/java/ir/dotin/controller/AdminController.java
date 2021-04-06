package ir.dotin.controller;

import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Employee;
import ir.dotin.service.AdminService;
import ir.dotin.service.EmployeeService;
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
    boolean usernameRepetitive;

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
            case "insertEmployee":
                insertEmployee(request, response, usernameRepetitive);
                break;
            case "getAllActiveEmployees":
                getAllActiveEmployees(request, response);
                break;
            case "active":
                active(request, response);
                break;
            case "inActive":
                inActive(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            case "editAndAppointmentOfManager":
                editAndAppointmentOfManager(request, response);
                break;
            case "updateProfile":
                updateProfile(request, response);
                break;
            case "search":
                search(request, response);
                break;
        }
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        Employee employee = new Employee();
            String userName = request.getParameter("username");
            Validation usernameValidation = new Validation();
            boolean invalidationUsername = usernameValidation.checkUsername(userName);

            if (invalidationUsername) {
                request.getSession().setAttribute("invalidationUsername", invalidationUsername);
                usernameRepetitive = true;
                insertEmployee(request, response, usernameRepetitive);
                return;
            }
            employee.setUsername(userName);
            insertEmployee(request, response,usernameRepetitive);
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
                    (request.getParameter("role"),session);
            employee.setRole(role);
            boolean Status = Boolean.valueOf(request.getParameter("disabled"));
            employee.setDisabled(Status);
            employee.setActive(false);
            String g = request.getParameter("gender");
            CategoryElement gender = searchCategoryElement.findCategoryElement(g,session);
            employee.setGender(gender);
            String[] managerDetail = request.getParameter("getManagerDetail").split("  ");
            employee.setManager(adminService.getManagerDetail(managerDetail[0], managerDetail[1],session));
            adminService.addUser(employee,session);
            getAllActiveEmployees(request, response);
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


    public void insertEmployee(HttpServletRequest request, HttpServletResponse response, boolean usernameRepetitive) throws IOException, ServletException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        List<String> allManager = allManager();
        request.setAttribute("managerList", allManager);
            String messages = "success";
            request.setAttribute(messages, true);
        RequestDispatcher rs = request.getRequestDispatcher("insertEmployee.jsp");
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

    public List<String> allManager() {
        Transaction transaction = null;
        Session session = null;
        List<String> managerInformation = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        List<Employee> allManager = adminService.allManager(session);
        for (Employee manager : allManager) {
            String managerName = manager.getFirstName() + "  " + manager.getLastName();
            managerInformation.add(managerName);
        }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
        return managerInformation;
    }


    public void getAllActiveEmployees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        request.setAttribute("AllActiveEmployees", adminService.getAllActiveEmployees(session));
        RequestDispatcher rs = request.getRequestDispatcher("adminManagement.jsp");
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

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Employee employee = adminService.getUserDetails(Long
                    .parseLong(request.getParameter("employeeId")),session);
            employee.setDisabled(false);
        adminService.active(employee,session);
            transaction.commit();
            search(request, response);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }
    public void inActive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Employee employee = adminService.getUserDetails(Long
                    .parseLong(request.getParameter("employeeId")),session);
            employee.setDisabled(true);
            adminService.inActive(employee,session);
            transaction.commit();
            search(request, response);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Employee employee = adminService.getUserDetails(Long
                    .parseLong(request.getParameter("employeeId")),session);
            employee.setActive(true);
            adminService.delete(employee,session);
            getAllActiveEmployees(request, response);
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


    public void editAndAppointmentOfManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        List<String> allManager = allManager();
        request.setAttribute("managerList", allManager);
        long employeeId = Long.parseLong(request.getParameter("employeeId"));
        Employee employee = adminService.searchId(employeeId,session);
        Employee manager = employee.getManager();
            if (manager != null) {
            request.setAttribute("Manager", manager.getFirstName() + "  " + manager.getLastName());
        }
        request.setAttribute("Employee", employee);
        RequestDispatcher rs = request.getRequestDispatcher("editAndAppointmentOfManager.jsp");
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

    public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            CategoryElement role = searchCategoryElement.findCategoryElement
                    (request.getParameter("role"), session);
            String[] managerData = request.getParameter("getManagerDetail").split("  ");
            Employee getManagerDetail = adminService.
                    getManagerDetail(managerData[0], managerData[1],session);

            Employee employee = employeeService.getUserDetails(Long
                    .parseLong(request.getParameter("id")),session);
            employee.setFirstName(request.getParameter("firstName"));
            employee.setLastName(request.getParameter("lastName"));
            employee.setEmail(request.getParameter("email"));
            employee.setFatherName(request.getParameter("fatherName"));
            employee.setRole(role);
            employee.setManager(getManagerDetail);
            adminService.updateUserDetails(employee,session);
            getAllActiveEmployees(request, response);
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
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        Employee employee = new Employee();
        String firstName = request.getParameter("firstName");
        employee.setFirstName(firstName);
        String lastName = request.getParameter("lastName");
        employee.setLastName(lastName);
        String username = request.getParameter("username");
        employee.setUsername(username);
        List<Employee> employees = adminService.searchEmployee(employee,session);
        request.setAttribute("employeeList", employees);
        request.setAttribute("firstName", request.getParameter("firstName"));
        request.setAttribute("lastName", request.getParameter("lastName"));
        request.setAttribute("username", request.getParameter("username"));
        RequestDispatcher rs = request.getRequestDispatcher("adminManagement.jsp");
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
