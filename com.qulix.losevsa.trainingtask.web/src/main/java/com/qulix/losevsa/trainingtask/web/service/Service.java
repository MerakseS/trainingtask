package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

/**
 * The interface for service layer, that used for working with business logic.
 *
 * @param <T>   the type parameter of object
 */
public interface Service<T> {

    /**
     * Create object from data.
     *
     * @param t the object to create.
     */
    void create(T t);

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
     * @param t the object to update.
     */
    void update(T t);

    /**
     * Delete object by id.
     *
     * @param id the id of the object.
     */
    void delete(long id);
}
