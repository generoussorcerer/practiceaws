package servlet.commands;

import model.dao.ClientDAO;
import model.dao.ReceiptDAO;
import model.entity.Client;
import servlet.MainServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginCommand implements Command
{
    @Override
    public String getPattern() {
        return "login";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException
    {
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/WEB-INF/view/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response,
                       ServletContext servletContext) throws ServletException, IOException {
        ClientDAO clientDAO = new ClientDAO();

        String name = request.getParameter("inputName");
        String password = request.getParameter("inputPassword");

        Client client = clientDAO.getClientRole(name, password);
        if (client == null)
        {
            doGet(request, response, servletContext);
        }
        else
        {
            int role = client.getRole();
            request.setAttribute("role", role);

            Cookie roleCookie = new Cookie("roleUser", URLEncoder.encode(String.valueOf(role), "UTF-8"));

            if (role == 2)
            {
                Cookie clientIdCookie = new Cookie("clientID",
                        URLEncoder.encode(String.valueOf(client.getId()), "UTF-8"));

                response.addCookie(clientIdCookie);
            }
            startNewSession(request, response);
            response.addCookie(roleCookie);
            response.sendRedirect(request.getContextPath() + "/serv?command=home");
        }
    }

    private void startNewSession (HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException
    {
        HttpSession session = request.getSession(true);
        int role = 1;
        int clientID = 0;

        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals("roleUser"))
                {
                    role = Integer.parseInt(cookie.getValue());
                }
                else if (cookie.getName().equals("clientID"))
                {
                    clientID = Integer.parseInt(cookie.getValue());
                }
            }
        }

        session.setAttribute("role", role);
        session.setAttribute("clientID", clientID);
        System.out.println("Session ID:" + session.getId());
    }

}
