package com.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskServlet extends HttpServlet {

    private final List<String> tasks = new ArrayList<>();

    public TaskServlet() {
        tasks.add("Finish Assignments");
        tasks.add("Study For Exam");
        tasks.add("Clean Up");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        String complete = request.getParameter("complete");

        if (complete != null) {
            int index = Integer.parseInt(complete);

            if (index >= 0 && index < tasks.size()
                    && !tasks.get(index).endsWith(" - Completed")) {
                tasks.set(index, tasks.get(index) + " - Completed");
            }

            response.sendRedirect("/tasks");
            return;
        }

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Task Tracker</title></head>");
        out.println("<body>");
        out.println("<h1>My Task Tracker - Maven Build</h1>");
        out.println("<h2>Tasks</h2>");
        out.println("<ul>");

        for (int i = 0; i < tasks.size(); i++) {
            out.println("<li>");
            out.println(tasks.get(i));

            if (!tasks.get(i).endsWith(" - Completed")) {
                out.println(" <a href='/tasks?complete=" + i + "'>Mark Complete</a>");
            }

            out.println("</li>");
        }

        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }
}