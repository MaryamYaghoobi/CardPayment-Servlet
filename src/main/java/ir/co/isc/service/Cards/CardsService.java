package ir.co.isc.service.Cards;

import ir.co.isc.entity.Cards;
import ir.co.isc.repository.CardsDao;
import org.hibernate.Session;

import java.util.List;

public class CardsService {
    private static CardsService cardsService = new CardsService();

    public static CardsService getInstance() {
        return cardsService;
    }

    private CardsService() {
    }

    public static List<Cards> searchCards(Cards cards, Session session) {
        CardsDao cardsDao = new CardsDao();
        return cardsDao.searchCards(cards, session);
    }
    public Cards getSelectedIssuerName(String issuerCode, String issuerName) {
        CardsDao cardsDao =new CardsDao();
        return cardsDao.getSelectedIssuerName(issuerCode,issuerName);
    }
    public void saveCard(Cards cards){
        CardsDao cardsDao =new CardsDao();
        cardsDao.save(cards);

    }
    public List<Cards> findAllCards(){
        CardsDao cardsDao =new CardsDao();
        return cardsDao.findAll();
    }
   /* public List<Object[]> findBinCards(String nationalCode){
        CardsDao cardsDao = new CardsDao();
        return cardsDao.findBinCards(nationalCode);
    }*/
   public List<String> findBinCards(String nationalCode){
       CardsDao cardsDao = new CardsDao();
       return cardsDao.findBinCards(nationalCode);
   }
   /* public List<Object[]> findTypeCards(String nationalCode){
        CardsDao cardsDao = new CardsDao();
        return cardsDao.findTypeCards(nationalCode);
    }*/
   public List<String> findTypeCards(String issuerCode){
       CardsDao cardsDao = new CardsDao();
       return cardsDao.findTypeCards(issuerCode);
   }
}
