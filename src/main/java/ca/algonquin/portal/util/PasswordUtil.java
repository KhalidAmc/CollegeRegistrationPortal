package ca.algonquin.portal.util;

import org.mindrot.jbcrypt.BCrypt;

/** Centralizes password hashing and verification. */
public final class PasswordUtil {
    private PasswordUtil() {}

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String plainPassword, String hash) {
        return plainPassword != null && hash != null && BCrypt.checkpw(plainPassword, hash);
    }
}
