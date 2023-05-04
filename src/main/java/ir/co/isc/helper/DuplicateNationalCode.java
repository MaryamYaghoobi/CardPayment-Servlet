package ir.co.isc.helper;

import ir.co.isc.exceptions.DuplicateCardNumberException;
import ir.co.isc.service.CardsService;
import ir.co.isc.service.CustomersService;
import org.apache.log4j.Logger;
import java.util.List;

public class DuplicateNationalCode {
    Logger logger = Logger.getRootLogger();

    public boolean checkDuplicateNationalCode(String nationalCodeEntered) {
        boolean invalidNationalCode = false;
      /*  Transaction transaction = null;
        Session session = null;*/
        try {
           /* session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();*/
            List<String> allSavedNationalCodes = CustomersService.getInstance().findAllNationalCode();
            for (String nationalCode : allSavedNationalCodes) {
                if (nationalCodeEntered.equals(nationalCode)) {
                    invalidNationalCode = true;
                    logger.info("nationalCode " + nationalCodeEntered + " is used before.");
                }
         /*   }
            transaction.commit();
        } finally {
            session.close();
        }*/

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return invalidNationalCode;
    }

    public boolean checkDuplicateCardsBin(String customersNationalCode, String cardTypeEntered) {

        boolean duplicateBinCard = false;
        boolean duplicateTypeCard = false;
       /* Transaction transaction = null;
        Session session = null;*/
        try {
          /*  session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();*/

            List<String> binCardsNumber = CardsService.getInstance().findBinCards(customersNationalCode);
            for (String customersBinCard : binCardsNumber)
                if (customersNationalCode.equals(customersBinCard)) {
                    logger.info("This issuer existed" + duplicateBinCard);
                    duplicateBinCard = true;
                }
            if (duplicateBinCard) {
                List<String> cardType = CardsService.getInstance().findTypeCards(String.valueOf(binCardsNumber));
                for (String cards : cardType)
                    if (cardTypeEntered.equals(cards))
                        duplicateTypeCard = true;
                logger.info("This type card exists for" + duplicateTypeCard);
             /*   transaction.commit();
                session.close();
*/
            }
        } catch (DuplicateCardNumberException c) {
            logger.error("card didnt save " + c.getMessage());
        }
        return duplicateTypeCard;
    }
}


