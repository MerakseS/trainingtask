package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Task;

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
     * @throws IncorrectInputException if data is incorrect
     */
    void createTask(String name, String strProjectId, String workTime, String startDate,
        String endDate, String status, String strEmployeeId);

    /**
     * Gets all tasks.
     *
     * @return the {@link List} of all tasks
     * @throws IncorrectInputException if there is no connection to com.qulix.losevsa.trainingtask.web.database
     */
    List<Task> getAllTasks();

    /**
     * Gets task by task id.
     *
     * @param taskId the task id
     * @return the task
     * @throws IncorrectInputException if task with that id doesn't exists
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
     * @throws IncorrectInputException if data is incorrect
     */
    void updateTask(long taskId, String name, String strProjectId, String workTime, String startDate,
        String endDate, String status, String strEmployeeId);

    /**
     * Delete task by task id.
     *
     * @param taskId the task id
     * @throws IncorrectInputException if task with that id doesn't exists
     */
    void deleteTask(long taskId);
}
