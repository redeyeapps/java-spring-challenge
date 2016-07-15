package co.redeye.spring.challenge.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides custom database access methods we require. Methods declared below are defined by Spring Framework magic.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Retrieves the user with the given username.
     * @param username The user to retrieve.
     * @return The user or null if they do not exist.
     */
    User findByUsername(String username);

    /**
     * Retrieves the user currently using the given authentication token.
     * @param token The user provided token.
     * @return The user or null if no user is using this token.
     */
    User findByToken(String token);
}
