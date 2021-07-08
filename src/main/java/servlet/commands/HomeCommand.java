package servlet.commands;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeCommand implements Command
{
    @Override
    public String getPattern() {
        return "home";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);

        if (Integer.parseInt(session.getAttribute("role").toString()) == 1)
        {
            RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/WEB-INF/view/homeAdmin.jsp");
            dispatcher.forward(request, response);
        }
        else if (Integer.parseInt(session.getAttribute("role").toString()) == 2)
        {
            RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/WEB-INF/view/homeClient.jsp");
            dispatcher.forward(request, response);
        }
    }
}
