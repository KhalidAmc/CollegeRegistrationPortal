package ca.algonquin.portal.service;

import ca.algonquin.portal.repository.InMemoryPortalRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationServiceTest {
    @Test
    void demoStudentCanLogin() {
        AuthenticationService service = new AuthenticationService(InMemoryPortalRepository.getInstance());
        assertTrue(service.login("student@algonquinlive.com", "Student123!").isPresent());
    }
}
