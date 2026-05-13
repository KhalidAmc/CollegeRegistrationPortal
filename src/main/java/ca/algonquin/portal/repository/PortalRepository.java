package ca.algonquin.portal.repository;

import ca.algonquin.portal.model.Course;
import ca.algonquin.portal.model.Enrollment;
import ca.algonquin.portal.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository abstraction.
 * This makes the app easy to migrate from in-memory data to MySQL/JDBC later.
 */
public interface PortalRepository {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(long id);
    User createUser(String fullName, String email, String passwordHash);

    List<Course> findAllCourses();
    List<Course> searchCourses(String keyword);
    Optional<Course> findCourseById(long courseId);
    Course saveCourse(Course course);
    void deactivateCourse(long courseId);

    List<Enrollment> findEnrollmentsByUserId(long userId);
    int countEnrollmentsByCourseId(long courseId);
    boolean isUserEnrolled(long userId, long courseId);
    Enrollment enroll(long userId, long courseId);
    void drop(long userId, long courseId);
}
