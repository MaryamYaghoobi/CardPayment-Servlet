package ir.co.isc.repository;

import ir.co.isc.entity.Customers;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BankerDao {

    public List<Customers> searchCurtomers(Customers customers, Session session) {
        List<Customers> customer = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Customers> Query = criteriaBuilder.createQuery(Customers.class);
        Root<Customers> emp = Query.from(Customers.class);
        Predicate finalPredicate = criteriaBuilder.conjunction();

        if (customers.getFirstName() != null && !customers.getFirstName().equals("")) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("firstName"), customers.getFirstName()));
        }
        if (customers.getLastName() != null && !customers.getLastName().equals("")) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("lastName"), customers.getLastName()));
        }
        if (customers.getAccountNumber() != null && !customers.getAccountNumber().equals("")) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("accountNumber"), customers.getAccountNumber()));
        }
        if (customers.getNationalCode() != null && !customers.getNationalCode().equals("")) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("nationalCode"), customers.getNationalCode()));
        }
        Query.select(emp).where(finalPredicate);
        org.hibernate.Query<Customers> query = session.createQuery(Query);
        customer = query.getResultList();
        return customer;

    }

}











