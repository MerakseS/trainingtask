package com.qulix.losevsa.trainingtask.web.repository;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Task;

/**
 * The interface of com.qulix.losevsa.trainingtask.web.repository layer for working with {@link Task}
 */
public interface TaskRepository {

    /**
     * Save task to com.qulix.losevsa.trainingtask.web.database.
     *
     * @param task the task
     * @return the task
     */
    Task saveTask(Task task);

    /**
     * Gets all tasks from com.qulix.losevsa.trainingtask.web.database.
     *
     * @return the all tasks
     */
    List<Task> getAllTasks();

    /**
     * Gets task list by project id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param projectId the project id
     * @return the task list by project id
     */
    List<Task> getTaskListByProjectId(long projectId);

    /**
     * Gets task by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the task by id
     */
    Task getTaskById(long id);

    /**
     * Update task in com.qulix.losevsa.trainingtask.web.database.
     *
     * @param task the task
     * @return the task
     */
    Task updateTask(Task task);

    /**
     * Delete task by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the long
     */
    long deleteTaskById(long id);
}
