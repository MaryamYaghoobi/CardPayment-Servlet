package ir.dotin.service;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.repository.EmailDao;

import java.util.List;

public class EmailService {
    public EmailService() {
    }

    public  void addMessages(Email email){
        EmailDao emailDao = new EmailDao();
        emailDao.addMessages(email);
    }
    public List<Email> messagesReceived(Employee employee){
        EmailDao emailDao = new EmailDao();
        return emailDao.messagesReceived(employee);
    }
    public  List<Object[]> detailsMessagesReceived(Employee employee){
        EmailDao emailDao = new EmailDao();
        return emailDao.detailsMessagesReceived(employee);
    }
    public  List<Object[]> detailsMessagesSent(Employee employee){
        EmailDao emailDao = new EmailDao();
        return emailDao.detailsMessagesSent(employee);
    }


}
