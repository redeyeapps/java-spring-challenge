package co.redeye.spring.challenge.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides custom database access methods we require.
 */
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    /**
     * Query to retrieve all to do list items belonging to a specific user with a given status.
     *
     * @param user The user.
     * @param done The item's status
     * @return The user's to do list items with the given status.
     */
    List<Item> findByUserAndDone(User user, boolean done);
}
