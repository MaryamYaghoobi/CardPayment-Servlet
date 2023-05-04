package ir.co.isc.controller;

import ir.co.isc.entity.Cards;
import ir.co.isc.entity.CategoryElement;
import ir.co.isc.entity.Customers;
import ir.co.isc.exceptions.DontSaveCardsException;
import ir.co.isc.helper.GenerationCVV2;
import ir.co.isc.helper.GenerationCardNumber;
import ir.co.isc.helper.utils.HibernateUtil;
import ir.co.isc.service.BankerService;
import ir.co.isc.service.CardsService;
import ir.co.isc.service.CategoryElementService;
import ir.co.isc.helper.DuplicateNationalCode;
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
import java.time.LocalDate;
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
        GenerationCVV2 generationCVV2 = new GenerationCVV2();
        GenerationCardNumber generationCardNumber = new GenerationCardNumber();
        //if nationalCode existed
        String customerNationalCode = request.getParameter("customerNationalCode");
        String[] cardsBin = request.getParameter("getSelectedIssuerName").split("  ");
        DuplicateNationalCode duplicateNationalCode = new DuplicateNationalCode();
        boolean dublicatedNationalCode = duplicateNationalCode.checkDuplicateNationalCode(customerNationalCode);
//If there is a national code, you must:
//Check the issuer code
//Check the type of card for this issuer
        if (dublicatedNationalCode) {
            boolean checkCard = duplicateNationalCode.checkDuplicateCardsBin(customerNationalCode, String.valueOf(cardsBin));
            if (checkCard) {
                logger.info("This card exists for" + customerNationalCode);
            }
        } else {
            //If there is no national code, you must:
            //Register the card for this national code
            //todo تاریخ انقضا
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            String expiredDate = request.getParameter("expirationDate");
            LocalDate expirationDate = null;
            if (expiredDate != null && !expiredDate.equals("")) {
                try {
                    expirationDate = formatter.parse(expiredDate);
                } catch (ParseException e) {
                    logger.error("String can not parse to Data" + e.getMessage());
                }
            }

            LocalDate expiredDate = LocalDate.now();

            CategoryElement cardType = CategoryElementService.getInstance().findByCodeCategory(request.getParameter("cardType"));
            CategoryElement cardStatus = CategoryElementService.getInstance().findByCodeCategory(request.getParameter("cardStatus"));

            Cards selectedBinCard = CardsService.getInstance().getSelectedIssuerName(cardsBin[0], cardsBin[1]);
            StringBuilder CardNumber = new StringBuilder(String.valueOf(selectedBinCard));
            CardNumber.append(selectedBinCard);
            int checkDigit = generationCardNumber.getCheckDigit(CardNumber.toString());
            CardNumber.append(checkDigit);
            card.setExpirationDate(expirationDate);
            card.setCardType(cardType);
            card.setCardStatus(cardStatus);
            card.setCardNumber(String.valueOf(CardNumber));
            card.setCvv2(String.valueOf(generationCVV2.generateCVV2()));
            try {
                CardsService.getInstance().saveCard(card);
                findAll(request, response);
            } catch (DontSaveCardsException d) {
                logger.error("card didnt save " + d.getMessage());
            }
        }
    }
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("cardList", CardsService.getInstance().findAllCards());

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
