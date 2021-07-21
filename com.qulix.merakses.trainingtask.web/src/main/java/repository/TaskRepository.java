package repository;

import entity.Task;

import java.util.List;

/**
 * The interface of repository layer for working with {@link Task}
 */
public interface TaskRepository {

    /**
     * Save task to database.
     *
     * @param task the task
     * @return the task
     */
    Task saveTask(Task task);

    /**
     * Gets all tasks from database.
     *
     * @return the all tasks
     */
    List<Task> getAllTasks();

    /**
     * Gets task list by project id from database.
     *
     * @param projectId the project id
     * @return the task list by project id
     */
    List<Task> getTaskListByProjectId(long projectId);

    /**
     * Gets task by id from database.
     *
     * @param id the id
     * @return the task by id
     */
    Task getTaskById(long id);

    /**
     * Update task in database.
     *
     * @param task the task
     * @return the task
     */
    Task updateTask(Task task);

    /**
     * Delete task by id from database.
     *
     * @param id the id
     * @return the long
     */
    long deleteTaskById(long id);
}
