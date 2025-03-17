package com.example.messaging.servlet;

import com.example.messaging.manager.UserManager;
import com.example.messaging.utils.MessageValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/message")
public class MessageServlet extends HttpServlet {
    private static final UserManager userManager = UserManager.getInstance();
    private static final MessageValidator messageValidator = MessageValidator.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        List<String> messages = userManager.getMessages(username, password);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(messages));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String message = request.getParameter("message");

        if (!messageValidator.isValid(message) || !userManager.userExists(username)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        userManager.addMessage(username, message);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
