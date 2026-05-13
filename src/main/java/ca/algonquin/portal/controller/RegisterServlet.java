package ca.algonquin.portal.controller;

import ca.algonquin.portal.model.User;
import ca.algonquin.portal.util.AppContext;
import ca.algonquin.portal.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Handles self-service student account registration. */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletUtil.view(request, response, "register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            User user = AppContext.authService().registerStudent(
                    request.getParameter("fullName"),
                    request.getParameter("email"),
                    request.getParameter("password")
            );
            request.getSession(true).setAttribute(ServletUtil.SESSION_USER, user);
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            ServletUtil.view(request, response, "register.jsp");
        }
    }
}
