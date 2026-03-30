package org.example.calcettomanagmentsystem.core.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic Data Access Object (DAO) interface for basic CRUD operations.
 * <p>
 * This interface defines standard methods for persisting and retrieving entities
 * from the data source, promoting consistency across specific DAO implementations.
 * </p>
 *
 * @param <T> The type of domain object this DAO manages.
 *
 * @author Alex Kerschbamer
 * @version 0.1
 * @since 1.0
 */
public interface GeneralDao<T> {
        /**
         * Persists the provided object to the database.
         * <p>
         * If the object is new, it should be inserted; if it already exists, 
         * its state should be updated.
         * </p>
         *
         * @param obj The domain object to save or update.
         * @return An {@link Optional} containing the persisted object with its potentially 
         *         generated identifier, or empty if the operation failed.
         */
        Optional<T> save(T obj);

        /**
         * Deletes the specified object from the persistent data store.
         *
         * @param obj The domain object to remove.
         * @return {@code true} if the deletion was successful; {@code false} if the 
         *         object could not be found or deletion failed.
         */
        boolean delete(T obj);

        /**
         * Retrieves all instances of type {@code T} from the database.
         *
         * @return A {@link List} containing all entities found in the data store.
         */
        List<T> findAll();

        /**
         * Searches for a single entity by its unique identifier.
         *
         * @param id The unique ID of the domain object to find.
         * @return An {@link Optional} containing the found entity, or empty if no 
         *         entity exists with the given ID.
         */
        Optional<T> findById(int id);
}
