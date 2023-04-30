package ir.co.isc.service;

import ir.co.isc.entity.CategoryElement;
import ir.co.isc.repository.CategoryElementDao;
import org.hibernate.Session;

public class CategoryElementService {
    private static CategoryElementService categoryElementService = new CategoryElementService();

    public static CategoryElementService getInstance() {
        return categoryElementService;
    }

    private CategoryElementService() {
    }
    public CategoryElement findByCodeCategory(String code){
        CategoryElementDao categoryElementDao=new CategoryElementDao();
        return categoryElementDao.findByCode(code);
    }
}
