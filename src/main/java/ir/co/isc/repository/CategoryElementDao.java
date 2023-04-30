package ir.co.isc.repository;

import ir.co.isc.entity.CategoryElement;
import ir.co.isc.helper.utils.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;

public class CategoryElementDao {
    public CategoryElement findByCode(String code) {

        CategoryElement categoryElement = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query query = session.createQuery("select ce from CategoryElement ce where ce.code=:categoryCode");
            query.setParameter("categoryCode", code);
            categoryElement = (CategoryElement) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryElement;
    }
}




