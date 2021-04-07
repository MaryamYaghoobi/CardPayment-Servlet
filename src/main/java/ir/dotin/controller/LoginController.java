package ir.dotin.controller;


import ir.dotin.entity.CategoryElement;
import ir.dotin.entity.Employee;
import ir.dotin.service.AdminService;
import ir.dotin.service.ManagerService;
import ir.dotin.service.SearchCategoryElement;
import ir.dotin.share.HibernateUtil;
import ir.dotin.share.Validation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private ManagerService managerService;
    AdminService adminService=new AdminService();
    SearchCategoryElement searchCategoryElement=new SearchCategoryElement();
    boolean usernameRepetitive;
    public void init() {
        managerService = new ManagerService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String action = null;
        action = request.getParameter("action");
        switch (action) {
            case "login":
                login(request, response);
                break;
            case "addAccount":
                addAccount(request, response);
                break;
            case "insertUser":
                insertUser(request, response);
                break;
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Employee employee = managerService.login(username, password);
        HttpSession session = request.getSession();
        try {
            if (employee != null) {
                session.setAttribute("username", username);
                if (employee.getRole().getCode().equals("manager")) {
                    RequestDispatcher rs = request.getRequestDispatcher("managerDashboard.jsp");
                    rs.forward(request, response);
                } else if (employee.getRole().getCode().equals("admin")) {
                    RequestDispatcher rs = request.getRequestDispatcher("adminManagement.jsp");
                    rs.forward(request, response);
                } else {
                    RequestDispatcher rs = request.getRequestDispatcher("employeeDashboard.jsp");
                    rs.forward(request, response);
                }
            } else {
                String message = "invalidationUserOrPassword";
                request.setAttribute(message, true);
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        }
    catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void addAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                insertUser(request, response);
                return;
            }
            employee.setUsername(userName);
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
            employee.setDisabled(false);
            employee.setActive(false);
            String g = request.getParameter("gender");
            CategoryElement gender = searchCategoryElement.findCategoryElement(g,session);
            employee.setGender(gender);
           adminService.addUser(employee,session);
            String messages = "addSuccess";
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


    public void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Transaction transaction = null;
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String messages = "success";
            request.setAttribute(messages, true);
            RequestDispatcher rs = request.getRequestDispatcher("newAccount.jsp");
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



