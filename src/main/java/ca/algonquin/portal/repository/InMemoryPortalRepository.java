package ca.algonquin.portal.repository;

import ca.algonquin.portal.model.Course;
import ca.algonquin.portal.model.Enrollment;
import ca.algonquin.portal.model.Role;
import ca.algonquin.portal.model.User;
import ca.algonquin.portal.util.PasswordUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Thread-safe in-memory repository for portfolio/demo use.
 * It avoids requiring MySQL just to test the project locally.
 */
public class InMemoryPortalRepository implements PortalRepository {
    private static final InMemoryPortalRepository INSTANCE = new InMemoryPortalRepository();

    private final AtomicLong userSequence = new AtomicLong(1);
    private final AtomicLong courseSequence = new AtomicLong(1);
    private final AtomicLong enrollmentSequence = new AtomicLong(1);

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Course> courses = new ConcurrentHashMap<>();
    private final Map<Long, Enrollment> enrollments = new ConcurrentHashMap<>();

    public static InMemoryPortalRepository getInstance() {
        return INSTANCE;
    }

    private InMemoryPortalRepository() {
        seedUsers();
        seedCourses();
    }

    private void seedUsers() {
        createSeedUser("Khalid Student", "student@algonquinlive.com", "Student123!", Role.STUDENT);
        createSeedUser("Portal Administrator", "admin@algonquinlive.com", "Admin123!", Role.ADMIN);
    }

    private void createSeedUser(String name, String email, String password, Role role) {
        long id = userSequence.getAndIncrement();
        users.put(id, new User(id, name, email.toLowerCase(), PasswordUtil.hash(password), role, LocalDateTime.now()));
    }

    private void seedCourses() {
        saveCourse(new Course(0, "CST8288", "Object-Oriented Programming", "Java OOP principles, inheritance, interfaces, and reusable design.", "Winter 2026", 3, 30, true));
        saveCourse(new Course(0, "CST8915", "Cloud-Native Applications", "Containers, Kubernetes, microservices, and message-driven architecture.", "Winter 2026", 3, 25, true));
        saveCourse(new Course(0, "CST8916", "Cloud Data Streaming", "IoT ingestion, stream processing, storage, and dashboard visualization.", "Winter 2026", 3, 20, true));
        saveCourse(new Course(0, "CST8109", "Network Programming", "Network concepts, protocols, and server-side troubleshooting.", "Spring 2026", 3, 28, true));
        saveCourse(new Course(0, "CST8390", "Database Systems", "Relational database design, SQL queries, normalization, and transactions.", "Spring 2026", 3, 24, true));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        if (email == null) return Optional.empty();
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }

    @Override
    public Optional<User> findUserById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User createUser(String fullName, String email, String passwordHash) {
        long id = userSequence.getAndIncrement();
        User user = new User(id, fullName, email.toLowerCase(), passwordHash, Role.STUDENT, LocalDateTime.now());
        users.put(id, user);
        return user;
    }

    @Override
    public List<Course> findAllCourses() {
        return courses.values().stream()
                .filter(Course::isActive)
                .sorted(Comparator.comparing(Course::getCode))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> searchCourses(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAllCourses();
        }
        String value = keyword.toLowerCase();
        return findAllCourses().stream()
                .filter(course -> course.getCode().toLowerCase().contains(value)
                        || course.getTitle().toLowerCase().contains(value)
                        || course.getDescription().toLowerCase().contains(value))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Course> findCourseById(long courseId) {
        return Optional.ofNullable(courses.get(courseId));
    }

    @Override
    public Course saveCourse(Course course) {
        long id = course.getId() == 0 ? courseSequence.getAndIncrement() : course.getId();
        Course saved = new Course(id, course.getCode(), course.getTitle(), course.getDescription(), course.getTerm(), course.getCredits(), course.getCapacity(), course.isActive());
        courses.put(id, saved);
        return saved;
    }

    @Override
    public void deactivateCourse(long courseId) {
        Course course = courses.get(courseId);
        if (course != null) {
            course.setActive(false);
        }
    }

    @Override
    public List<Enrollment> findEnrollmentsByUserId(long userId) {
        return enrollments.values().stream()
                .filter(enrollment -> enrollment.getUserId() == userId)
                .sorted(Comparator.comparing(Enrollment::getEnrolledAt).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int countEnrollmentsByCourseId(long courseId) {
        return (int) enrollments.values().stream()
                .filter(enrollment -> enrollment.getCourseId() == courseId)
                .count();
    }

    @Override
    public boolean isUserEnrolled(long userId, long courseId) {
        return enrollments.values().stream()
                .anyMatch(enrollment -> enrollment.getUserId() == userId && enrollment.getCourseId() == courseId);
    }

    @Override
    public Enrollment enroll(long userId, long courseId) {
        Enrollment enrollment = new Enrollment(enrollmentSequence.getAndIncrement(), userId, courseId, LocalDateTime.now());
        enrollments.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    @Override
    public void drop(long userId, long courseId) {
        enrollments.values().removeIf(enrollment -> enrollment.getUserId() == userId && enrollment.getCourseId() == courseId);
    }
}
