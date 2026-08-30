package com.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class TaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Task Tracker</title></head>");
        out.println("<body>");
        out.println("<h1>My Task Tracker</h1>");
        out.println("<p>My task management application</p>");

        out.println("<h2>Tasks</h2>");
        out.println("<ul>");
        out.println("<li>Finish Assignments - Not Completed</li>");
        out.println("<li>Study For Test - Not Completed</li>");
        out.println("<li>Clean Up - Not Completed</li>");
        out.println("</ul>");

        out.println("</body>");
        out.println("</html>");
    }
}