package ca.algonquin.portal.service;

import ca.algonquin.portal.model.Course;
import ca.algonquin.portal.model.Enrollment;
import ca.algonquin.portal.repository.PortalRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/** Handles registration and drop rules for students. */
public class EnrollmentService {
    private final PortalRepository repository;

    public EnrollmentService(PortalRepository repository) {
        this.repository = repository;
    }

    public void enroll(long userId, long courseId) {
        Course course = repository.findCourseById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));
        if (!course.isActive()) {
            throw new IllegalArgumentException("This course is no longer active.");
        }
        if (repository.isUserEnrolled(userId, courseId)) {
            throw new IllegalArgumentException("You are already enrolled in this course.");
        }
        if (repository.countEnrollmentsByCourseId(courseId) >= course.getCapacity()) {
            throw new IllegalArgumentException("This course is full.");
        }
        repository.enroll(userId, courseId);
    }

    public void drop(long userId, long courseId) {
        repository.drop(userId, courseId);
    }

    public List<Course> coursesForStudent(long userId) {
        return repository.findEnrollmentsByUserId(userId).stream()
                .map(Enrollment::getCourseId)
                .map(id -> repository.findCourseById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
