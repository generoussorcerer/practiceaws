package servlet.filters;

import model.dao.ClientDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter
public class SignUpFilter implements Filter
{
    @Override
    public void destroy()
    {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException
    {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String url = request.getQueryString();
        if (url != null && url.equals("command=signUp"))
        {

            String name = req.getParameter("inputName");
            String password = req.getParameter("inputPassword");
            String roleValue = req.getParameter("inputRole");

            System.out.println("name" + name);
            System.out.println("password" + password);
            System.out.println("role C" + roleValue);
            System.out.println("HIIIII sign");

            if (name == null || name.length() < 3)
            {
                System.out.println("HIIIII name");
                RequestDispatcher rd = request.getRequestDispatcher("/serv?command=signUp");
                rd.forward(request, response);
                return;
            }
            else if (password.length() < 4)
            {
                System.out.println("HIIIII pass");
                RequestDispatcher rd = request.getRequestDispatcher("/serv?command=signUp");
                rd.forward(request, response);
                return;
            }
            else
            {
                System.out.println("HIIIII DAO");
                ClientDAO clientDAO = new ClientDAO();
                int role;

                if (roleValue.equals("1"))
                    role = 1;
                else
                    role = 2;

                if (clientDAO.signUpClient(name, password, role))
                {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/serv?command=index");
                    dispatcher.forward(request, response);
                }
                else
                {
                    RequestDispatcher rd = request.getRequestDispatcher("/serv?command=signUp");
                    rd.forward(request, response);
                }
            }
        }
        else
        {
            chain.doFilter(request, response);
        }
    }
}