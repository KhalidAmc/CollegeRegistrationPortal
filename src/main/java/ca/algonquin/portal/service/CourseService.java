package ca.algonquin.portal.service;

import ca.algonquin.portal.model.Course;
import ca.algonquin.portal.repository.PortalRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Contains course catalog and admin course-management business logic. */
public class CourseService {
    private final PortalRepository repository;

    public CourseService(PortalRepository repository) {
        this.repository = repository;
    }

    public List<Course> searchCourses(String keyword) {
        return repository.searchCourses(keyword);
    }

    public Course addCourse(String code, String title, String description, String term, int credits, int capacity) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Course code is required.");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Course title is required.");
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be greater than zero.");
        if (credits <= 0) throw new IllegalArgumentException("Credits must be greater than zero.");
        return repository.saveCourse(new Course(0, code.trim().toUpperCase(), title.trim(), description.trim(), term.trim(), credits, capacity, true));
    }

    public void deactivateCourse(long courseId) {
        repository.deactivateCourse(courseId);
    }

    /** Builds a map of course id to number of enrolled students for easy JSP rendering. */
    public Map<Long, Integer> seatCounts(List<Course> courses) {
        Map<Long, Integer> counts = new LinkedHashMap<>();
        for (Course course : courses) {
            counts.put(course.getId(), repository.countEnrollmentsByCourseId(course.getId()));
        }
        return counts;
    }
}
