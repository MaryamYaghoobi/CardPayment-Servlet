package ir.dotin.repository;

import ir.dotin.entity.Email;
import ir.dotin.entity.Employee;
import ir.dotin.share.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmailDao {
    public void addMessages(Email email) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(email);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    public List<Email> messagesReceived(Employee employee) {
        List<Email> receivedEmails = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select  " +
                    "email from Email email join email.receiverEmployees employee  " +
                    "  where employee.id =:id");
            query.setParameter("id", employee.getId());
            receivedEmails = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receivedEmails;
    }

    public List<Object[]> detailsMessagesReceived(Employee employee) {
        List<Object[]> receivedEmailsInfo = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select email.id ," +
                    " sender.firstName , sender.lastName , email.context , email.subject from Email email , " +
                    "Employee sender join email.receiverEmployees receiver join sender.sentEmails se  " +
                    "  where receiver.id =:id AND se.id = email.id");
            query.setParameter("id", employee.getId());
            receivedEmailsInfo = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receivedEmailsInfo;
    }


    public List<Object[]> detailsMessagesSent(Employee employee) {
        List<Object[]> sentEmailsInfo = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("select email.id " +
                    " ,receiver.c_firstName,receiver.c_lastName,email.c_context,email.c_subject \n" +
                    "from dotin.t_employee receiver inner join \n" +
                    "dotin.mm_employeeemail employee_email on receiver.id = employee_email.c_receiverId " +
                    "inner join dotin.t_email email on email.id=employee_email.c_receiveEmailId inner join\n" +
                    "dotin.t_employee sender on sender.id = email.c_emailSenderId where sender.id =:id");
            query.setParameter("id", employee.getId());
            sentEmailsInfo = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentEmailsInfo;
    }
}

