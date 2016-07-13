package co.redeye.spring.challenge.db;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Matthew Johnson on 13/07/2016.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    User findByToken(String token);
}
