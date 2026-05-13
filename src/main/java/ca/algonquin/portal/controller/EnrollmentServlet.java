package ca.algonquin.portal.controller;

import ca.algonquin.portal.model.User;
import ca.algonquin.portal.util.AppContext;
import ca.algonquin.portal.util.ServletUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Handles enrolling in a course from the catalog. */
@WebServlet("/enroll")
public class EnrollmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = ServletUtil.currentUser(request);
        try {
            long courseId = ServletUtil.parseLong(request.getParameter("courseId"), "Invalid course id.");
            AppContext.enrollmentService().enroll(user.getId(), courseId);
            request.getSession().setAttribute("success", "Course added to your schedule.");
        } catch (IllegalArgumentException ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/courses");
    }
}
