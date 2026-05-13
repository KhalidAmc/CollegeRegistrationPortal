package ca.algonquin.portal.model;

import java.time.LocalDateTime;

/** Represents a student's registration in a course. */
public class Enrollment {
    private final long id;
    private final long userId;
    private final long courseId;
    private final LocalDateTime enrolledAt;

    public Enrollment(long id, long userId, long courseId, LocalDateTime enrolledAt) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
    }

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public long getCourseId() { return courseId; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
}
