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
 */
public interface Repository<T> {
    /**
     * Persists the provided object.
     *
     * @param obj The object to save.
     * @return An {@link Optional} containing the persisted object, or empty if saving failed.
     */
    Optional<T> save(T obj);

    /**
     * Deletes the specified object.
     *
     * @param obj The object to delete.
     * @return {@code true} if deletion was successful; {@code false} otherwise.
     */
    boolean delete(T obj);

    /**
     * Retrieves all instances of type T.
     *
     * @return A {@link List} containing all found entities.
     */
    List<T> findAll();

    /**
     * Searches for an entity by its unique identifier.
     *
     * @param id The unique ID of the entity.
     * @return An {@link Optional} containing the entity if found, or empty otherwise.
     */
    Optional<T> findById(int id);
}