package ir.dotin.service;

import ir.dotin.entity.CategoryElement;
import ir.dotin.repository.CategoryElementDao;

public class searchCategoryElement {
    public static CategoryElement findCategoryElement(String code){
        CategoryElementDao categoryElementDao=new CategoryElementDao();
        return categoryElementDao.searchCategoryElement(code);
    }
}
