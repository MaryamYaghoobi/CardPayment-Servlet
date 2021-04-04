package ir.dotin.repository;

import ir.dotin.entity.CategoryElement;
import org.hibernate.Session;

import javax.persistence.Query;

public class CategoryElementDao {
    public CategoryElement searchCategoryElement(String code, Session session) {
        CategoryElement categoryElementList = null;
        Query query = session.createQuery
                ("select c from CategoryElement c where c.code=:category");
        query.setParameter("category", code);
        categoryElementList = (CategoryElement) query.getSingleResult();
        return categoryElementList;

    }
}



