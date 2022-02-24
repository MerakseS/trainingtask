package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.exception.DateParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.EndDateEarlierStartDateException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NoProjectException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;
import com.qulix.losevsa.trainingtask.web.service.exception.StatusParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeNegativeException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeParseException;

/**
 * The interface of business logic for working with {@link Task}.
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
     *
     * @throws FieldNotFilledException if required fields are empty
     * @throws NoProjectException if project id is incorrect
     * @throws NameLengthExceededException if name's length is bigger than 30.
     * @throws WorkTimeParseException if work time input is incorrect
     * @throws WorkTimeNegativeException if work time is negative
     * @throws DateParseException if start date or end date is incorrect
     * @throws EndDateEarlierStartDateException if end date is earlier than start date
     * @throws StatusParseException if status is incorrect
     * @throws StatusParseException if status is incorrect
     */
    void createTask(String name, String strProjectId, String workTime, String startDate,
        String endDate, String status, String strEmployeeId);

    /**
     * Gets all tasks.
     *
     * @return the {@link List} of all tasks
     */
    List<Task> getAllTasks();

    /**
     * Gets task by task id.
     *
     * @param taskId the task id
     * @return the task
     * @throws NotFoundException if task with that id doesn't exist
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
     *
     * @throws NotFoundException if task with that id doesn't exist
     * @throws FieldNotFilledException if required fields are empty
     * @throws NoProjectException if project id is incorrect
     * @throws NameLengthExceededException if name's length is bigger than 30.
     * @throws WorkTimeParseException if work time input is incorrect
     * @throws WorkTimeNegativeException if work time is negative
     * @throws DateParseException if start date or end date is incorrect
     * @throws EndDateEarlierStartDateException if end date is earlier than start date
     * @throws StatusParseException if status is incorrect
     * @throws StatusParseException if status is incorrect
     */
    void updateTask(long taskId, String name, String strProjectId, String workTime, String startDate,
        String endDate, String status, String strEmployeeId);

    /**
     * Delete task by task id.
     *
     * @param taskId the task id
     * @throws NotFoundException if task with that id doesn't exist
     */
    void deleteTask(long taskId);
}
