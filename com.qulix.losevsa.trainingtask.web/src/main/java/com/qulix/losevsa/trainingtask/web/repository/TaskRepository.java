package com.qulix.losevsa.trainingtask.web.repository;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Task;

/**
 * The interface of repository layer for working with {@link Task}
 */
public interface TaskRepository {

    /**
     * Save task to database.
     *
     * @param task the task
     * @return the task
     * @throws QueryExecutionException if a database access error occurs
     */
    Task saveTask(Task task);

    /**
     * Gets all tasks from database.
     *
     * @return the all tasks
     * @throws QueryExecutionException if a database access error occurs
     */
    List<Task> getAllTasks();

    /**
     * Gets task list by project id from database.
     *
     * @param projectId the project id
     * @return the task list by project id
     * @throws QueryExecutionException if a database access error occurs
     */
    List<Task> getTaskListByProjectId(long projectId);

    /**
     * Gets task by id from database.
     *
     * @param id the id
     * @return the task by id
     * @throws QueryExecutionException if a database access error occurs
     */
    Task getTaskById(long id);

    /**
     * Update task in database.
     *
     * @param task the task
     * @return the task
     * @throws QueryExecutionException if a database access error occurs
     */
    Task updateTask(Task task);

    /**
     * Delete task by id from database.
     *
     * @param id the id
     * @return the long
     * @throws QueryExecutionException if a database access error occurs
     */
    long deleteTaskById(long id);
}
