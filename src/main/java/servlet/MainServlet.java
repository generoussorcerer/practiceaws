package servlet;

import servlet.commands.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet
public class MainServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private Map<String, Command> actions;

    public MainServlet()
    {
        super();
        this.actions = new HashMap<>();
    }

    @Override
    public void init()
    {
        Command[] actions = {new IndexCommand()};

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
            actions.get("index").doGet(request, response, this.getServletContext());
            System.out.println("Opening command: " + "index");
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
}

