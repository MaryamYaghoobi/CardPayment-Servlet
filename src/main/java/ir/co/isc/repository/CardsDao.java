package ir.co.isc.repository;

import ir.co.isc.entity.Cards;
import ir.co.isc.entity.Customers;
import ir.co.isc.helper.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CardsDao {
    public List<Cards> searchCards(Cards cards, Session session) {
        List<Cards> card = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Cards> Query = criteriaBuilder.createQuery(Cards.class);
        Root<Cards> emp = Query.from(Cards.class);
        Predicate finalPredicate = criteriaBuilder.conjunction();

        if (cards.getCardNumber() != null && !cards.getCardNumber().equals("")) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("cardNumber"), cards.getCardNumber()));
        }
        if (cards.getIssuerName() != null && !cards.getIssuerName().equals("")) {
            finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(emp.get("issuerName"), cards.getIssuerName()));
        }

        Query.select(emp).where(finalPredicate);
        org.hibernate.Query<Cards> query = session.createQuery(Query);
        card = query.getResultList();
        return card;

    }
    public Cards getSelectedIssuerName(String issuerCode, String issuerName) {
        Cards selectedIssuerName = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select c from Cards c where c.issuerCode =: issuerCode and c.issuerName =:issuerName");
            query.setParameter("issuerCode", issuerCode);
            query.setParameter("issuerName", issuerName);
            selectedIssuerName = (Cards) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedIssuerName;
    }
    public void save(Cards cards) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cards);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public List<Cards> findAll() {
        List<Cards> cardsList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("SELECT c FROM Cards c where c.cardStatus.code =:code", Cards.class);
            query.setParameter("code", "active");
            cardsList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardsList;
    }
    public List<Object[]> findBinCards(String nationalCode) {
        List<Object[]> binList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select Cards.cardNumber , Cards.issuerName from Customers c , Cards join c.cardsList  cc join Cards .customers  ca " +
                    "  where ca.nationalCode=:nationalCode");
            query.setParameter("nationalCode", nationalCode);
            binList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binList;
    }
    public List<Object[]> findTypeCards(String nationalCode) {
        List<Object[]> binList = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("select Cards.cardNumber , Cards.cardType from Customers c , Cards join c.cardsList  cc join Cards .customers  ca " +
                    "  where ca.nationalCode=:nationalCode");
            query.setParameter("nationalCode", nationalCode);
            binList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binList;
    }
}
