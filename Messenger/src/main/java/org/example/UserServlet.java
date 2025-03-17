package org.example;

import com.example.messaging.manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final UserManager userManager = UserManager.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userManager.userExists(username)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("The entered username is already registered");
            return;
        }
        userManager.addUser(username, password);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("You have successfully registered");
    }
}
