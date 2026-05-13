package ca.algonquin.portal.controller;

import ca.algonquin.portal.model.User;
import ca.algonquin.portal.util.AppContext;
import ca.algonquin.portal.util.ServletUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/** Handles dropping a course from the student dashboard. */
@WebServlet("/drop")
public class DropCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = ServletUtil.currentUser(request);
        long courseId = ServletUtil.parseLong(request.getParameter("courseId"), "Invalid course id.");
        AppContext.enrollmentService().drop(user.getId(), courseId);
        request.getSession().setAttribute("success", "Course removed from your schedule.");
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
