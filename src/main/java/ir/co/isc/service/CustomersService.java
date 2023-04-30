package ir.co.isc.service;

import ir.co.isc.repository.CustomersDao;

import java.util.List;

public class CustomersService {

    public List<String> findAllNationalCode() {
        CustomersDao customersDao = new CustomersDao();
        return customersDao.findAllNationalCode();
    }

    private static CustomersService customersService = new CustomersService();

    public static CustomersService getInstance() {
        return customersService;
    }
}



