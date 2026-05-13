package ca.algonquin.portal.util;

import ca.algonquin.portal.repository.InMemoryPortalRepository;
import ca.algonquin.portal.repository.PortalRepository;
import ca.algonquin.portal.service.AuthenticationService;
import ca.algonquin.portal.service.CourseService;
import ca.algonquin.portal.service.EnrollmentService;

/** Simple service locator used to keep servlets small without adding a DI framework. */
public final class AppContext {
    private static final PortalRepository REPOSITORY = InMemoryPortalRepository.getInstance();
    private static final AuthenticationService AUTH_SERVICE = new AuthenticationService(REPOSITORY);
    private static final CourseService COURSE_SERVICE = new CourseService(REPOSITORY);
    private static final EnrollmentService ENROLLMENT_SERVICE = new EnrollmentService(REPOSITORY);

    private AppContext() {}

    public static AuthenticationService authService() { return AUTH_SERVICE; }
    public static CourseService courseService() { return COURSE_SERVICE; }
    public static EnrollmentService enrollmentService() { return ENROLLMENT_SERVICE; }
    public static PortalRepository repository() { return REPOSITORY; }
}
