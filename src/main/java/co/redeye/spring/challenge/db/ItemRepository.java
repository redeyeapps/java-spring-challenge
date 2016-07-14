package co.redeye.spring.challenge.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides custom database access methods we require.
 */
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
