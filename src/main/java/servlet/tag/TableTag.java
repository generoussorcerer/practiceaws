package servlet.tag;

import model.dao.ReceiptDAO;
import model.entity.Receipt;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TableTag extends BodyTagSupport
{
    private boolean admin;
    public void setAdmin(String admin)
    {
        this.admin = new Boolean(admin);
    }

    public int doStartTag() throws JspTagException
    {
        try
        {
            pageContext.getOut().write("<table class=\"table\">");
            pageContext.getOut().write("<thead><tr>\n" +
                    "                                <th scope=\"col\">ID</th>\n" +
                    "                                <th scope=\"col\">Is Paid</th>\n" +
                    "                                <th scope=\"col\">Total</th>\n" +
                    "                                <th scope=\"col\">Client ID</th>\n" +
                    "                                <th scope=\"col\">Room ID</th>\n" +
                    "                                <th scope=\"col\">Booking ID</th>\n" +
                    "                            </tr>\n" +
                    "                            </thead>\n" +
                    "                            <tbody>");
        }
        catch (IOException e)
        {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspTagException
    {

        try
        {
            ReceiptDAO receiptDAO = new ReceiptDAO();
            List<Receipt> list = receiptDAO.getNotPaidReceipts();

            for (Receipt r : list)
            {
                pageContext.getOut().write("<tr>\n" +
                        "<td name=\"user_id\" scope=\"col\"><div contenteditable>" + r.getId() + "</div></td>\n" +
                        "<td name=\"user_paid\" scope=\"col\"><div contenteditable>" + r.isPaid() + "</div></td>\n" +
                        "<td name=\"user_total\" scope=\"col\"><div contenteditable>" + r.getTotal() + "</div></td>\n" +
                        "<td name=\"user_client\" scope=\"col\"><div contenteditable>" + r.getClientId().getId() + "</div></td>\n" +
                        "<td name=\"user_room\" scope=\"col\"><div contenteditable>" + r.getRoomId().getId() + "</div></td>\n" +
                        "<td name=\"user_booking\" scope=\"col\"><div contenteditable>" + r.getBookingId().getId() + "</div></td>\n" +
                        "</tr>");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException
    {
        try
        {
            pageContext.getOut().write("</tbody></table>");
        }
        catch (IOException e)
        {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}

