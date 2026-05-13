package ca.algonquin.portal.controller;

import ca.algonquin.portal.model.Course;
import ca.algonquin.portal.model.Role;
import ca.algonquin.portal.model.User;
import ca.algonquin.portal.util.AppContext;
import ca.algonquin.portal.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/** Displays a role-aware dashboard after login. */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = ServletUtil.currentUser(request);
        List<Course> registeredCourses = AppContext.enrollmentService().coursesForStudent(user.getId());
        List<Course> allCourses = AppContext.courseService().searchCourses("");

        request.setAttribute("registeredCourses", registeredCourses);
        request.setAttribute("totalCourses", allCourses.size());
        request.setAttribute("isAdmin", user.getRole() == Role.ADMIN);
        ServletUtil.view(request, response, "dashboard.jsp");
    }
}
