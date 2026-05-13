package ca.algonquin.portal.model;

import java.time.LocalDateTime;

/**
 * Represents an application user.
 * In a production application, the password hash would be persisted in a database.
 */
public class User {
    private final long id;
    private final String fullName;
    private final String email;
    private final String passwordHash;
    private final Role role;
    private final LocalDateTime createdAt;

    public User(long id, String fullName, String email, String passwordHash, Role role, LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
