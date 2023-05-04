package ir.co.isc.controller;

import ir.co.isc.entity.Cards;
import ir.co.isc.entity.CategoryElement;
import ir.co.isc.entity.Customers;
import ir.co.isc.helper.utils.GenerationCVV2;
import ir.co.isc.helper.utils.GenerationCardNumber;
import ir.co.isc.service.BankerService;
import ir.co.isc.service.CardsService;
import ir.co.isc.service.CategoryElementService;
import ir.co.isc.helper.utils.HibernateUtil;
import ir.co.isc.helper.utils.DuplicateNationalCode;
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
        GenerationCVV2 generationCVV2 = new GenerationCVV2();
        String customerNationalCode = request.getParameter("customerNationalCode");
        DuplicateNationalCode duplicateNationalCode = new DuplicateNationalCode();
        boolean doublicateNationalCode = duplicateNationalCode.checkDuplicateNationalCode(customerNationalCode);

        if (doublicateNationalCode) {
            //اگر کد ملی تکراری باشد
            //باید شرط ها در اینجا بررسی گردد
            ////ایا با این شناسه صادر کننده شماره کارت دارد
            // که ایا با این نوع شماره کارت وجود دارد
            //شرط ها اعمال گردد

            return;
        }
// اگر کد ملی وجود نداشته باشد در اینجا شماره کارت باید ایجاد گردد
        // برای ایجاد شماره کارت :
        // باید تاریخ انقضا توسط بانکدار ثبت گردد
        // باید cvv2 و alhvi ;vhj لثدثقشفث a,n
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

//در متد ایجاد شماره کارت قرار گرفته است
       /* String[] cardsBin = request.getParameter("getSelectedIssuerName").split("  ");
        Cards selectedManager = CardsService.getInstance().getSelectedEmployeeManager(cardsBin[0], cardsBin[1]);
        */
        CategoryElement cardType = CategoryElementService.getInstance().findByCodeCategory(request.getParameter("cardType"));
        CategoryElement cardStatus = CategoryElementService.getInstance().findByCodeCategory(request.getParameter("cardStatus"));

        String[] cardsBin = request.getParameter("getSelectedIssuerName").split("  ");
        Cards selectedBinCard = CardsService.getInstance().getSelectedIssuerName(cardsBin[0], cardsBin[1]);
        GenerationCardNumber generationCardNumber = new GenerationCardNumber();
        StringBuilder CardNumber = new StringBuilder(String.valueOf(selectedBinCard));
        CardNumber.append(selectedBinCard);
        int checkDigit = generationCardNumber.getCheckDigit(CardNumber.toString());
        CardNumber.append(checkDigit);

        // Cards card = new Cards(request.getParameter("CardNumber"),request.getParameter("CVV2"),expirationDate, cardType, cardStatus);
        card.setExpirationDate(expirationDate);
        card.setCardType(cardType);
        card.setCardStatus(cardStatus);
        card.setCardNumber(String.valueOf(CardNumber));
        card.setCvv2(String.valueOf(generationCVV2.generateCVV2()));
        try {
            CardsService.getInstance().saveCard(card);
            findAll(request, response);
        } catch (Exception e) {
            logger.error("card didnt save " + e.getMessage());
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
