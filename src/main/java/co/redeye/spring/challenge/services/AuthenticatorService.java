package co.redeye.spring.challenge.services;

import co.redeye.spring.challenge.db.User;
import co.redeye.spring.challenge.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Handles all tasks related to user authentication.
 */
@Service
public class AuthenticatorService {
    private static final int SALT_LENGTH = 64;
    private static final int TOKEN_LENGTH = 32;

    // Constants for random String generation
    private static final int UNIQUE_CHARACTERS = 10 + 26 + 26; //Numerals, lowercase and uppercase letters
    private static final int NUMERAL_CUTOFF = 10;
    private static final int LOWERCASE_CUTOFF = NUMERAL_CUTOFF + 26;

    @Autowired
    private UserRepository userRepository;

    private final Random random;
    private final MessageDigest hashEncoder;

    public AuthenticatorService() {
        random = new Random();
        try {
            hashEncoder = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
            //Impossible
            throw new RuntimeException(ignored);
        }
    }


    /**
     * Registers a new user.
     *
     * @param username The user's desired username.
     * @param password The user's desired password.
     * @return The user's authentication token.
     * @throws AuthenticationException When the specified username is taken.
     */
    @Transactional
    public String register(String username, String password) throws AuthenticationException {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new AuthenticationException("This username has already been taken.");
        }

        String salt = randomString(SALT_LENGTH);
        String saltedPassword = generateSaltedPassword(salt, password);
        String token = randomString(TOKEN_LENGTH);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(saltedPassword);
        newUser.setSalt(salt);
        newUser.setToken(token);

        newUser = userRepository.save(newUser);
        return token;
    }

    /**
     * Authenticates a user and returns a token.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return The user's authentication token.
     * @throws AuthenticationException When either credential is invalid.
     */
    public String login(String username, String password) throws AuthenticationException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("Authentication error");
        }

        String saltedPassword = generateSaltedPassword(user.getSalt(), password);
        if (!saltedPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Authentication error");
        }

        String token = randomString(TOKEN_LENGTH);
        user.setToken(token);
        userRepository.save(user);
        return token;
    }

    /**
     * Generates the salted password hash.
     *
     * @param salt The salt to use on the password.
     * @param password The user's password.
     * @return The salted password hash.
     */
    private String generateSaltedPassword(String salt, String password) {
        return new String(hashEncoder.digest((salt + password).getBytes()));
    }

    /**
     * Generates a random string of alphanumberic characters
     *
     * @return The generated string.
     */
    private String randomString(int length) {
        StringBuilder salt = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            int current = random.nextInt(UNIQUE_CHARACTERS);
            if (current < NUMERAL_CUTOFF) {
                salt.append((char) ('0' + current));
            } else if (current < LOWERCASE_CUTOFF) {
                salt.append((char) ('a' + current - NUMERAL_CUTOFF));
            } else {
                salt.append((char) ('A' + current - LOWERCASE_CUTOFF));
            }
        }
        return salt.toString();
    }
}
