package ca.algonquin.portal.controller;

import ca.algonquin.portal.model.Course;
import ca.algonquin.portal.util.AppContext;
import ca.algonquin.portal.util.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/** Allows administrators to add or deactivate courses. */
@WebServlet("/admin/courses")
public class AdminCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Course> courses = AppContext.courseService().searchCourses("");
        request.setAttribute("courses", courses);
        request.setAttribute("seatCounts", AppContext.courseService().seatCounts(courses));
        ServletUtil.view(request, response, "admin-courses.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                long id = ServletUtil.parseLong(request.getParameter("courseId"), "Invalid course id.");
                AppContext.courseService().deactivateCourse(id);
                request.getSession().setAttribute("success", "Course deactivated.");
            } else {
                AppContext.courseService().addCourse(
                        request.getParameter("code"),
                        request.getParameter("title"),
                        request.getParameter("description"),
                        request.getParameter("term"),
                        Integer.parseInt(request.getParameter("credits")),
                        Integer.parseInt(request.getParameter("capacity"))
                );
                request.getSession().setAttribute("success", "Course added successfully.");
            }
            response.sendRedirect(request.getContextPath() + "/admin/courses");
        } catch (RuntimeException ex) {
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        }
    }
}
