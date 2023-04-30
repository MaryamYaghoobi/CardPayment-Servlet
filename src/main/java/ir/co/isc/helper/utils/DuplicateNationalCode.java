package ir.co.isc.helper.utils;

import ir.co.isc.service.CardsService;
import ir.co.isc.service.CustomersService;
import org.apache.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DuplicateNationalCode  {
    Logger logger = Logger.getRootLogger();

    public boolean checkDuplicateNationalCode(String nationalCodeEntered) {
        boolean invalidNationalCode = false;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            List<String> allSavedNationalCodes = CustomersService.getInstance().findAllNationalCode();
            for (String nationalCode : allSavedNationalCodes) {
                if (nationalCodeEntered.equals(nationalCode)) {
                    invalidNationalCode = true;
                    logger.info("nationalCode " + nationalCodeEntered + " is used before.");
                }
            }
            transaction.commit();
        } finally {
        session.close();
    }
        return invalidNationalCode;
    }
    public boolean checkDuplicateCardsBin(String customerNationalCode) {

        boolean duplicateCustomerNationalCode = false;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

        List<Object[]> CardsNumber = CardsService.getInstance().findBinCards(customerNationalCode);
            for (Object[] nationalCode : CardsNumber) {
                if (customerNationalCode.equals(nationalCode)) {
                    duplicateCustomerNationalCode = true;
                    logger.info("This issuer exists for" + duplicateCustomerNationalCode );
                }
            }
            transaction.commit();
        } finally {
            session.close();
        }
        return duplicateCustomerNationalCode;
    }
}}}