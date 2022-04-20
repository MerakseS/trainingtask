package com.qulix.losevsa.trainingtask.web.repository;

import java.util.List;

/**
 * The interface Repository.
 *
 * @param <T>   the type parameter of object in repository
 */
public interface Repository<T> {

    /**
     * Save object to database.
     *
     * @param t the object to save.
     * @return the saved object
     * @throws QueryExecutionException if a database access error occurs
     */
    T save(T t);

    /**
     * Gets all objects from database.
     *
     * @return the {@link List} of all objects from database.
     * @throws QueryExecutionException if a database access error occurs
     */
    List<T> getAll();

    /**
     * Gets object by id from database.
     *
     * @param id the id of the object.
     * @return the object by id.
     * @throws QueryExecutionException if a database access error occurs
     */
    T getById(long id);

    /**
     * Update object in database.
     *
     * @param t the object to update.
     * @return the updated object.
     * @throws QueryExecutionException if a database access error occurs
     */
    T update(T t);

    /**
     * Delete object by id from database.
     *
     * @param id the id of the object.
     * @return the id of deleted object.
     * @throws QueryExecutionException if a database access error occurs
     */
    long deleteById(long id);
}
