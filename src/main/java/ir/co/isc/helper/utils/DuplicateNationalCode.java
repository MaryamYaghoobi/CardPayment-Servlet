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
    public boolean checkDuplicateCardsBin(String issuerCodeEntered,String cardTypeEntered) {

        boolean duplicateBinCard = false;
        boolean duplicateTypeCard = false;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            List<String> binCardsNumber = CardsService.getInstance().findBinCards(issuerCodeEntered);
            for (String binCard : binCardsNumber)
                if (issuerCodeEntered.equals(binCard)) {
                    logger.info("This issuer existed" + duplicateBinCard);
                    duplicateBinCard = true;
                }
            List<String> cardType = CardsService.getInstance().findTypeCards(String.valueOf(binCardsNumber));
            for (String cards : cardType)
                if (cardTypeEntered.equals(cards))
                    duplicateTypeCard = true;
            logger.info("This type card exists for" + duplicateTypeCard);

            transaction.commit();
        } finally {
            session.close();
        }

        return duplicateBinCard;
    }}



