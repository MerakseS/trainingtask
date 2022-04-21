package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

/**
 * The interface for service layer, that used for working with business logic.
 *
 * @param <T>   the type parameter of object
 * @param <S>   the type parameter of data transfer object
 */
public interface Service<T, S> {

    /**
     * Create object from data.
     *
     * @param s the data to create.
     */
    void create(S s);

    /**
     * Gets all objects.
     *
     * @return the {@link List} of all objects.
     */
    List<T> getAll();

    /**
     * Get object by id.
     *
     * @param id the id of the object.
     * @return the object by id.
     */
    T get(long id);

    /**
     * Update object by id.
     *
     * @param id the id of the object to update.
     * @param s the new data to update.
     */
    void update(long id, S s);

    /**
     * Delete object by id.
     *
     * @param id the id of the object.
     */
    void delete(long id);
}
