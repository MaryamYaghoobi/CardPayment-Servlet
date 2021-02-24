package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import ir.dotin.share.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;

public class CategoryElementDao {

    public CategoryElement searchCategoryElement(String code) {
        CategoryElement  categoryElementList=null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String strSelect = "from CategoryElement  where code=:categoryCode";
            Query query = session.createQuery(strSelect);
            query.setParameter("categoryCode", code);
              categoryElementList = (CategoryElement) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryElementList;
    }
}


