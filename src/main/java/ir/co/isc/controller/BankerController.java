package ir.co.isc.controller;

import ir.co.isc.entity.Cards;
import ir.co.isc.entity.CategoryElement;
import ir.co.isc.entity.Customers;
import ir.co.isc.exceptions.DontSaveCardsException;
import ir.co.isc.service.Cards.GenerationCVV2Service;
import ir.co.isc.service.Cards.GenerationCardNumberService;
import ir.co.isc.helper.utils.HibernateUtil;
import ir.co.isc.service.Banker.BankerService;
import ir.co.isc.service.Cards.CardsService;
import ir.co.isc.service.CategoryElement.CategoryElementService;
import ir.co.isc.service.Cards.CheckduplicationCardService;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@WebServlet("/AdminController")
public class BankerController extends HttpServlet {
    static Logger logger = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger = Logger.getRootLogger();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = null;
        action = request.getParameter("action");
        switch (action) {
            case "addCard":
                addCard(request, response);
                break;
            case "search":
                search(request, response);
                break;
        }
    }

    public void addCard(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cards card = new Cards();
        GenerationCVV2Service generationCVV2Service = new GenerationCVV2Service();
        GenerationCardNumberService generationCardNumberService = new GenerationCardNumberService();
        //if nationalCode existed
        String customerNationalCode = request.getParameter("customerNationalCode");
        String[] cardsBin = request.getParameter("getSelectedIssuerName").split("  ");
        CheckduplicationCardService checkduplicationCardService = new CheckduplicationCardService();
        boolean dublicatedNationalCode = checkduplicationCardService.checkDuplicateNationalCode(customerNationalCode);
//If there is a national code, you must:
//Check the issuer code
//Check the type of card for this issuer
        if (dublicatedNationalCode) {
            boolean checkCard = checkduplicationCardService.checkDuplicateCards(customerNationalCode, String.valueOf(cardsBin));
            if (checkCard) {
                logger.info("This card exists for" + customerNationalCode);
            }
        } else {
            //If there is no national code, you must:
            //Register the card for this national code
            SimpleDateFormat formatter = new SimpleDateFormat("MM/yy", Locale.ENGLISH);
            String dateInString = request.getParameter("expirationDate");
            Date expirationDate = null;
            if (dateInString != null && !dateInString.equals("")) {
                try {
                    expirationDate = formatter.parse(dateInString);
                } catch (ParseException p) {
                    logger.error("String can not parse to Data" + p.getMessage());
                }
            }
            CategoryElement cardType = CategoryElementService.getInstance().findByCodeCategory(request.getParameter("cardType"));
            CategoryElement cardStatus = CategoryElementService.getInstance().findByCodeCategory(request.getParameter("cardStatus"));

            Cards selectedBinCard = CardsService.getInstance().getSelectedIssuerName(cardsBin[0], cardsBin[1]);
            StringBuilder CardNumber = new StringBuilder(String.valueOf(selectedBinCard));
            CardNumber.append(selectedBinCard);
            int checkDigit = generationCardNumberService.getCheckDigit(CardNumber.toString());
            CardNumber.append(checkDigit);
            card.setCardType(cardType);
            card.setCardStatus(cardStatus);
            card.setExpirationDate(expirationDate);
            card.setCardNumber(String.valueOf(CardNumber));
            card.setCvv2(String.valueOf(generationCVV2Service.generateCVV2()));
            try {
                CardsService.getInstance().saveCard(card);
                findAll(request, response);
            } catch (DontSaveCardsException d) {
                logger.error("card didnt save " + d.getMessage());
            }
        }
    }

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cardList", CardsService.getInstance().findAllCards());

    }


    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Customers customer = new Customers();
            Cards card = new Cards();
            customer.setFirstName(request.getParameter("firstName"));
            customer.setLastName(request.getParameter("lastName"));
            customer.setAccountNumber(request.getParameter("accountNumber"));
            customer.setNationalCode(request.getParameter("nationalCode"));
            card.setCardNumber(request.getParameter("cardNumber"));
            card.setIssuerName(request.getParameter("issuerName"));
            List<Customers> customers = BankerService.getInstance().searchCustomer(customer, session);
            List<Cards> cards = CardsService.getInstance().searchCards(card, session);
            request.setAttribute("customersList", customers);
            request.setAttribute("cardsList", cards);
            request.setAttribute("firstName", request.getParameter("firstName"));
            request.setAttribute("lastName", request.getParameter("lastName"));
            request.setAttribute("accountNumber", request.getParameter("accountNumber"));
            request.setAttribute("nationalCode", request.getParameter("nationalCode"));
            request.setAttribute("cardNumber", request.getParameter("cardNumber"));
            request.setAttribute("issuerName", request.getParameter("issuerName"));
            RequestDispatcher rs = request.getRequestDispatcher("adminManagement.jsp");
            rs.forward(request, response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

    }
}
