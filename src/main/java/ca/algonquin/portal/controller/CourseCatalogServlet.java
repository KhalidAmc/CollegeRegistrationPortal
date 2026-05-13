package ca.algonquin.portal.controller;

import ca.algonquin.portal.model.Course;
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
import java.util.stream.Collectors;

/** Displays searchable course catalog. */
@WebServlet("/courses")
public class CourseCatalogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("q");
        User user = ServletUtil.currentUser(request);
        List<Course> courses = AppContext.courseService().searchCourses(keyword);
        List<Long> enrolledCourseIds = AppContext.enrollmentService().coursesForStudent(user.getId())
                .stream().map(Course::getId).collect(Collectors.toList());

        request.setAttribute("courses", courses);
        request.setAttribute("seatCounts", AppContext.courseService().seatCounts(courses));
        request.setAttribute("enrolledCourseIds", enrolledCourseIds);
        request.setAttribute("q", keyword == null ? "" : keyword);
        ServletUtil.view(request, response, "courses.jsp");
    }
}
