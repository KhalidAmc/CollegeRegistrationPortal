/**
 * Servlet implementation class LoginServlet.
 * 
 * <p>This servlet handles user login requests. It retrieves the email and 
 * password from the request parameters, delegates authentication to the 
 * {@link AuthenticationService}, and forwards the user to the appropriate 
 * page based on authentication success.</p>
 * 
 * <p>If authentication succeeds, the user's email is stored in the session 
 * and the user is forwarded to "index.jsp". If authentication fails, an 
 * error message is set as a request attribute and the user is forwarded back 
 * to "login.jsp".</p>
 * 
 * @author Jenna Beach (040777966)
 * @version 1.0
 * @since 2025-11-13
 */

package controller;

import service.AuthenticationService;
import service.AuthenticationServiceImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /** Service used to authenticate users. */
    private AuthenticationService authService = new AuthenticationServiceImpl();

    /**
     * Handles HTTP POST requests for user login.
     *
     * <p>This method retrieves "email" and "password" parameters from the request,
     * authenticates the user using {@link AuthenticationService#authenticate(String, String)},
     * and forwards the request to the appropriate JSP page.</p>
     *
     * @param request the {@link HttpServletRequest} object that contains the request the client made
     * @param response the {@link HttpServletResponse} object that contains the response the servlet sends
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error occurs while handling the request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /** Retrieve the user's email from the request parameters */
        String email = request.getParameter("email");

        /** Retrieve the user's password from the request parameters */
        String password = request.getParameter("password");

        /** Authenticate the user using the AuthenticationService */
        boolean isValid = authService.authenticate(email, password);

        if (isValid) {
            /** Store the user's email in the session to track logged-in state */
            request.getSession().setAttribute("user", email);

            /** Forward the authenticated user to the home page (index.jsp) */
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            /** Set an error message to notify the user of invalid login */
            request.setAttribute("error", "Invalid credentials");

            /** Forward the user back to the login page to try again */
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}