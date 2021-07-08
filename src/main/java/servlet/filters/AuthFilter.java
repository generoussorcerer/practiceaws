package servlet.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter
public class AuthFilter implements Filter
{
    @Override
    public void destroy()
    {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException
    {
        //filterConfig = fConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException
    {

        List<String> allowed = new ArrayList<>();
        allowed.add("null");
        allowed.add("command=login");
        allowed.add("command=signUp");
        allowed.add("language=en");
        allowed.add("language=ru");

        List<String> clientUrl = new ArrayList<>();
        clientUrl.add("command=payReceipt");
        clientUrl.add("command=createBooking");
        clientUrl.add("command=printRooms");

        List<String> adminUrl = new ArrayList<>();
        adminUrl.add("command=confirmBooking");
        adminUrl.add("command=confirmBookingRooms");
        adminUrl.add("command=unconfirmedBookings");
        adminUrl.add("command=unpaidReceipts");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String url = request.getQueryString();
        if (url == null)
            url = "null";


        System.out.println(url);

        HttpSession session = request.getSession(false);
        if ((session == null && !allowed.contains(url))
                ||  (!allowed.contains(url) && session.getAttribute("role") == null))
        {
            System.out.println("uno");

            response.sendRedirect(request.getContextPath() + "/serv");
            return;
        }
        else if ( session != null && session.getAttribute("role") != null
                && session.getAttribute("role").toString().equals("1") && clientUrl.contains(url))
        {
            System.out.println("duo");

            response.sendRedirect(request.getContextPath() + "/serv?command=home");
            return;
        }
        else if (session != null && session.getAttribute("role") != null
                && session.getAttribute("role").toString().equals("2") && adminUrl.contains(url))
        {
            System.out.println("tre");

            response.sendRedirect(request.getContextPath() + "/serv?command=home");
            return;
        }
        else
            chain.doFilter(req, res);
    }
}