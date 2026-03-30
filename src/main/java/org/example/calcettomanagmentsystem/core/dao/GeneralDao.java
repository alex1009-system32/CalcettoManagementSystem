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
 */
public interface GeneralDao<T> {
        /**
         * Persists the provided object to the database.
         *
         * @param obj The object to save.
         * @return An {@link Optional} containing the persisted object with its generated ID,
         *         or empty if saving failed.
         */
        Optional<T> save(T obj);

        /**
         * Deletes the specified object from the database.
         *
         * @param obj The object to delete.
         * @return {@code true} if deletion was successful; {@code false} otherwise.
         */
        boolean delete(T obj);

        /**
         * Retrieves all instances of type T from the database.
         *
         * @return A {@link List} containing all found entities.
         */
        List<T> findAll();

        /**
         * Searches for an entity by its unique identifier.
         *
         * @param id The unique ID of the object.
         * @return An {@link Optional} containing the entity if found, or empty otherwise.
         */
        Optional<T> findById(int id);
}
