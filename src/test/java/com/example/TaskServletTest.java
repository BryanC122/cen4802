package com.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServletTest {

    @Test
    void testInitialTasksAreDisplayed() throws Exception {
        TaskServlet servlet = new TaskServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);
        when(request.getParameter("complete")).thenReturn(null);

        servlet.doGet(request, response);

        String output = stringWriter.toString();

        assertTrue(output.contains("Finish Assignments"));
        assertTrue(output.contains("Study For Exam"));
        assertTrue(output.contains("Organize Office"));
    }

    @Test
    void testCompletingTask() throws Exception {
        TaskServlet servlet = new TaskServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("complete")).thenReturn("0");

        servlet.doGet(request, response);

        verify(response).sendRedirect("/tasks");
    }

    @Test
    void testInvalidTaskIndexDoesNotCrash() throws Exception {
        TaskServlet servlet = new TaskServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("complete")).thenReturn("99");

        assertDoesNotThrow(() -> servlet.doGet(request, response));

        verify(response).sendRedirect("/tasks");
    }
}