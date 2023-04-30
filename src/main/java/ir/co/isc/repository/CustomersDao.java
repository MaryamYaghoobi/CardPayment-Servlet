package ir.co.isc.repository;

import ir.co.isc.entity.Customers;

import ir.co.isc.helper.utils.HibernateUtil;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CustomersDao {
    public Customers updateVersion(long id, long version, Session session) {
        Customers Customers = null;
        Query querySelect = session.createQuery("select e from Customers e where e.id =:id");
        querySelect.setParameter("id", id);
        querySelect.setLockMode(LockModeType.OPTIMISTIC);
        querySelect.getResultList();
        Query queryUpdate = session.createQuery("update Customers e set " +
                "e.version = e.version+1 where e.id =:id and e.version=:version ");
        queryUpdate.setParameter("id", id);
        queryUpdate.setParameter("version", version);
        Customers = (Customers) queryUpdate.getResultList();
        return Customers;
    }
    //OK
    public List<String> findAllNationalCode() {
        List<String> nationalCode = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select c.nationalCode from Customers c ");
            nationalCode = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nationalCode;
    }


    public List<Customers> allCustomers(Session session) {
        List<Customers> CustomersList = new ArrayList<>();
        String allCustomers = "select e from Customers e";
        Query query = session.createQuery(allCustomers);
        CustomersList = query.getResultList();
        return CustomersList;
    }
 }
