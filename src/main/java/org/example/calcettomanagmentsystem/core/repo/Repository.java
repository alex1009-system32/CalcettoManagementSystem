package org.example.calcettomanagmentsystem.core.repo;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface defining standard data access operations.
 * <p>
 * This interface provides a common contract for repositories, facilitating 
 * consistent interaction with various domain entity types.
 * </p>
 *
 * @param <T> The type of domain object managed by the repository.
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public interface Repository<T> {
    /**
     * Persists the provided object to the underlying data store.
     * <p>
     * This method acts as a bridge to the corresponding DAO for saving operations.
     * </p>
     *
     * @param obj The domain entity to save.
     * @return An {@link Optional} containing the persisted entity, or empty if the 
     *         operation failed.
     */
    Optional<T> save(T obj);

    /**
     * Deletes the specified object from the data store.
     *
     * @param obj The domain entity to remove.
     * @return {@code true} if the deletion was successful; {@code false} otherwise.
     */
    boolean delete(T obj);

    /**
     * Retrieves all instances of type {@code T} from the repository.
     *
     * @return A {@link List} containing all found entities.
     */
    List<T> findAll();

    /**
     * Searches for a specific entity by its unique identifier.
     *
     * @param id The unique ID of the entity.
     * @return An {@link Optional} containing the entity if found, or empty otherwise.
     */
    Optional<T> findById(int id);
}