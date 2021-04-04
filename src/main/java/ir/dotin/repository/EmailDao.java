package ir.dotin.repository;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmailDao {
    public void addMessages(Email email, Session session) {
        session.persist(email);
    }

    public List<Email> messagesReceived(Employee employee, Session session) {
        List<Email> receivedEmails = new ArrayList<>();
        Query query = session.createQuery("select  " +
                "email from Email email join email.receiverEmployees employee  " +
                "  where employee.id =:id");
        query.setParameter("id", employee.getId());
        receivedEmails = query.getResultList();
        return receivedEmails;
    }


    public List<Object[]> detailsMessagesReceived(Employee employee, Session session) {
        List<Object[]> receivedEmailsInfo = new ArrayList<>();
        Query query = session.createQuery("select email.id ," +
                " sender.firstName , sender.lastName , email.context , email.subject from Email email , " +
                "Employee sender join email.receiverEmployees receiver join sender.sentEmails se  " +
                "  where receiver.id =:id AND se.id = email.id");
        query.setParameter("id", employee.getId());
        receivedEmailsInfo = query.getResultList();
        return receivedEmailsInfo;
    }


    public List<Object[]> detailsMessagesSent(Employee employee, Session session) {
        List<Object[]> sentEmailsInfo = new ArrayList<>();
        Query query = session.createSQLQuery("select email.id " +
                " ,receiver.c_firstName,receiver.c_lastName,email.c_context,email.c_subject \n" +
                "from t_employee receiver inner join \n" +
                "mm_employeeemail employee_email on receiver.id = employee_email.c_receiverId " +
                "inner join t_email email on email.id=employee_email.c_receiveEmailId inner join\n" +
                "t_employee sender on sender.id = email.c_emailSenderId where sender.id =:id");
        query.setParameter("id", employee.getId());
        sentEmailsInfo = query.getResultList();
        return sentEmailsInfo;
    }
}

