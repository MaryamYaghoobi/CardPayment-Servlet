package ir.dotin.service;

import ir.dotin.entity.CategoryElement;
import ir.dotin.repository.CategoryElementDao;
import org.hibernate.Session;

public class SearchCategoryElement {
    public CategoryElement findCategoryElement(String code, Session session) {
        CategoryElementDao categoryElementDao = new CategoryElementDao();
        return categoryElementDao.searchCategoryElement(code, session);
    }
}
