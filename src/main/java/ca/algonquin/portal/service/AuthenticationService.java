package ca.algonquin.portal.service;

import ca.algonquin.portal.model.User;
import ca.algonquin.portal.repository.PortalRepository;
import ca.algonquin.portal.util.PasswordUtil;

import java.util.Optional;
import java.util.regex.Pattern;

/** Handles login and registration business rules. */
public class AuthenticationService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private final PortalRepository repository;

    public AuthenticationService(PortalRepository repository) {
        this.repository = repository;
    }

    /** Returns the authenticated user when the email/password combination is valid. */
    public Optional<User> login(String email, String password) {
        return repository.findUserByEmail(email)
                .filter(user -> PasswordUtil.matches(password, user.getPasswordHash()));
    }

    /** Creates a student account after validating the submitted form fields. */
    public User registerStudent(String fullName, String email, String password) {
        validateRegistration(fullName, email, password);
        if (repository.findUserByEmail(email).isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }
        return repository.createUser(fullName.trim(), email.trim().toLowerCase(), PasswordUtil.hash(password));
    }

    private void validateRegistration(String fullName, String email, String password) {
        if (fullName == null || fullName.trim().length() < 3) {
            throw new IllegalArgumentException("Full name must contain at least 3 characters.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters.");
        }
    }
}
