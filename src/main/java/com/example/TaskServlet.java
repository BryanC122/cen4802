
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
        tasks.add("Finish Homework");
        tasks.add("Study For Exam");
        tasks.add("Repair Computer");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        String complete = request.getParameter("complete");

        if (complete != null) {
            try {
                int index = Integer.parseInt(complete);

                if (index >= 0 && index < tasks.size()
                        && !tasks.get(index).endsWith(" - Completed")) {
                    tasks.set(index, tasks.get(index) + " - Completed");
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            response.sendRedirect("/tasks");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Task Tracker</title></head>");
        out.println("<body>");
        out.println("<h1>My Task Tracker - Maven Build</h1>");
        out.println("<h2>Add a New Task</h2>");
        out.println("<form method='post' action='/tasks'>");
        out.println("<input type='text' name='task' required>");
        out.println("<button type='submit'>Add Task</button>");
        out.println("</form>");
        out.println("<h2>Tasks</h2>");
        out.println("<ul>");

        for (int i = 0; i < tasks.size(); i++) {
            out.println("<li>");
            out.println(escapeHtml(tasks.get(i)));

            if (!tasks.get(i).endsWith(" - Completed")) {
                out.println(" <a href='/tasks?complete=" + i + "'>Mark Complete</a>");
            }

            out.println("</li>");
        }

        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String task = request.getParameter("task");

        if (task == null || task.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Task name is required.");
            return;
        }

        tasks.add(task.trim());
        response.sendRedirect("/tasks");
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}