
package com.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(TaskServlet.class.getName());

    private final List<String> tasks = new ArrayList<>();

    public TaskServlet() {
        tasks.add("Finish Assignments");
        tasks.add("Study For Exam");
        tasks.add("Repair Computer");

        LOGGER.info("TaskServlet initialized. Initial task count: "
                + tasks.size());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        String complete = request.getParameter("complete");

        if (complete != null) {
            try {
                int index = Integer.parseInt(complete);

                if (index < 0 || index >= tasks.size()) {
                    LOGGER.warning("Invalid task completion index: "
                            + index + ". Current task count: " + tasks.size());
                    response.sendRedirect("/tasks");
                    return;
                }

                if (!tasks.get(index).endsWith(" - Completed")) {
                    tasks.set(index, tasks.get(index) + " - Completed");

                    LOGGER.info("Task marked completed. Index: " + index
                            + ", task: " + tasks.get(index));
                } else {
                    LOGGER.info("Task was already completed. Index: "
                            + index);
                }

            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING,
                        "Invalid completion parameter: " + complete, e);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Task index must be a number.");
                return;
            }

            response.sendRedirect("/tasks");
            return;
        }

        LOGGER.info("Task list requested. Task count: " + tasks.size());

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
                out.println(" <a href='/tasks?complete=" + i
                        + "'>Mark Complete</a>");
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
            LOGGER.warning("Task creation rejected: task name is missing "
                    + "or blank.");

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Task name is required.");
            return;
        }

        String taskName = task.trim();

        try {
            tasks.add(taskName);

            LOGGER.info("Task added successfully. Task name: \""
                    + taskName + "\", total tasks: " + tasks.size());

        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE,
                    "Unexpected error while adding a task.", e);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to add task.");
            return;
        }

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
