package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import ir.dotin.share.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;

public class CategoryElementDao {
    public CategoryElement searchCategoryElement(String code) {
        CategoryElement categoryElementList = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery
                    ("select c from CategoryElement c where c.code=:category");
            query.setParameter("category", code);
            categoryElementList = (CategoryElement) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryElementList;

    }
}



