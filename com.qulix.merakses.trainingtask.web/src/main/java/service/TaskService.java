package service;

import entity.Task;

import java.util.List;

/**
 * The interface of business logic for working with {@link Task}
 */
public interface TaskService {

    /**
     * Create the task.
     *
     * @param name          the name of task
     * @param strProjectId  the project id string
     * @param workTime      the work time
     * @param startDate     the start date of executing task
     * @param endDate       the end date of executing task
     * @param status        the status of task
     * @param strEmployeeId the employee id string
     * @return the task
     * @throws ServiceException if data is incorrect
     */
    Task createTask(String name, String strProjectId, String workTime, String startDate,
                    String endDate, String status, String  strEmployeeId);

    /**
     * Gets all tasks.
     *
     * @return the {@link List} of all tasks
     * @throws ServiceException if there is no connection to database
     */
    List<Task> getAllTasks();

    /**
     * Gets task by task id.
     *
     * @param taskId the task id
     * @return the task
     * @throws ServiceException if task with that id doesn't exists
     */
    Task getTask(long taskId);

    /**
     * Update task by task id.
     *
     * @param taskId        the task id
     * @param name          the name of task
     * @param strProjectId  the project id string
     * @param workTime      the work time
     * @param startDate     the start date of executing task
     * @param endDate       the end date of executing task
     * @param status        the status of task
     * @param strEmployeeId the employee id string
     * @return the task
     * @throws ServiceException if data is incorrect
     */
    Task updateTask(long taskId, String name, String strProjectId, String workTime, String startDate,
                    String endDate, String status, String strEmployeeId);

    /**
     * Delete task by task id.
     *
     * @param taskId the task id
     * @return id of the deleted task
     * @throws ServiceException if task with that id doesn't exists
     */
    long deleteTask(long taskId);
}
