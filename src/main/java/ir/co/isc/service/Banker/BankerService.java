package ir.co.isc.service.Banker;

import ir.co.isc.entity.Customers;
import ir.co.isc.repository.BankerDao;
import org.hibernate.Session;

import java.util.List;

public class BankerService {
    private static BankerService bankerService = new BankerService();

    public static BankerService getInstance() {
        return bankerService;
    }
    public List<Customers> searchCustomer(Customers customers, Session session) {
        BankerDao bankerDao = new BankerDao();
        return bankerDao.searchCurtomers(customers, session);
    }


}
