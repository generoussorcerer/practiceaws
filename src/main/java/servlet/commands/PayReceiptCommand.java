package servlet.commands;

import model.dao.ReceiptDAO;
import model.entity.Receipt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PayReceiptCommand implements Command
{
    @Override
    public String getPattern() {
        return "payReceipt";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response,
                      ServletContext servletContext) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/WEB-INF/view/payReceipt.jsp");

        HttpSession session = request.getSession(false);
        int clientID = Integer.parseInt(session.getAttribute("clientID").toString());

        ReceiptDAO receiptDAO = new ReceiptDAO();
        List<Receipt> list = receiptDAO.payReceiptList(clientID);
        request.setAttribute("payReceiptList", list);

        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response,
                       ServletContext servletContext) throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        int clientID = Integer.parseInt(session.getAttribute("clientID").toString());
        ReceiptDAO receiptDAO = new ReceiptDAO();

        int receiptId = Integer.parseInt(request.getParameter("receiptId"));
        receiptDAO.payReceiptCheck(receiptDAO.payReceiptList(clientID), receiptId);

        doGet(request, response, servletContext);
    }
}