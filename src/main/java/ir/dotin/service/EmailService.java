package ir.dotin.service;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.repository.EmailDao;
import org.hibernate.Session;

import java.util.List;

public class EmailService {
    public EmailService() {
    }

    public void addMessages(Email email, Session session) {
        EmailDao emailDao = new EmailDao();
        emailDao.addMessages(email, session);
    }

    public List<Email> messagesReceived(Employee employee, Session session) {
        EmailDao emailDao = new EmailDao();
        return emailDao.messagesReceived(employee, session);
    }

    public List<Object[]> detailsMessagesReceived(Employee employee, Session session) {
        EmailDao emailDao = new EmailDao();
        return emailDao.detailsMessagesReceived(employee, session);
    }

    public List<Object[]> detailsMessagesSent(Employee employee, Session session) {
        EmailDao emailDao = new EmailDao();
        return emailDao.detailsMessagesSent(employee, session);
    }


}
