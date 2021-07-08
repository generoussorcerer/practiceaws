package servlet;

import servlet.commands.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet
public class MainServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private Map<String, Command> actions;

    public MainServlet() {
        super();
        this.actions = new HashMap<>();
    }

    @Override
    public void init()
    {
        Command[] actions = {new IndexCommand(), new LoginCommand(), new SignUpCommand(),
                new HomeCommand(), new UnpaidReceiptsCommand(),
                new UnconfirmedBookingsCommand(), new PrintRoomsCommand(),
                new CreateBookingCommand(), new PayReceiptCommand(),
                new ConfirmBookingCommand(), new ConfirmBookingRoomsCommand()};

        for (Command c : actions)
        {
            this.actions.put(c.getPattern(), c);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String command = request.getParameter("command");
        if (command == null)
        {
            startCookies(request, response);
            actions.get("index").doGet(request, response, this.getServletContext());
            System.out.println("Opening command: " + "index");
        }
        else if (command.equals( "home"))
        {
            //startNewSession(request, response);
            actions.get("home").doGet(request, response, this.getServletContext());
            System.out.println("Opening command: " + "home");
        }
        else if (actions.containsKey(command))
        {
            actions.get(command).doGet(request, response, this.getServletContext());
            System.out.println("Opening command: " + command);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String command = request.getParameter("command");
        if (command == null)
        {
            actions.get("index").doGet(request, response, this.getServletContext());
            System.out.println("Posting command: " + "index");
        }
        else if (actions.containsKey(command))
        {
            actions.get(command).doPost(request, response, this.getServletContext());
            System.out.println("Posting command: " + command);
        }
    }


    protected void startCookies(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy/HH:mm:ss");
        Date date = new Date();

        Cookie lastEnterTime = new Cookie("lastEnterTime", formatter.format(date));
        lastEnterTime.setComment(URLEncoder.encode( "Last-visit:", "UTF-8" ));
        Cookie usageCount = new Cookie("usageCount", URLEncoder.encode( "1", "UTF-8" ));
        usageCount.setComment(URLEncoder.encode("Times-you-have-visited-our-website:", "UTF-8"));

        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals("usageCount"))
                {
                    int lastUsageCount = Integer.parseInt(cookie.getValue());
                    lastUsageCount += 1;
                    usageCount.setValue(URLEncoder.encode(Integer.toString(lastUsageCount), "UTF-8"));
                }
            }
        }
        response.addCookie(lastEnterTime);
        response.addCookie(usageCount);
    }

}

