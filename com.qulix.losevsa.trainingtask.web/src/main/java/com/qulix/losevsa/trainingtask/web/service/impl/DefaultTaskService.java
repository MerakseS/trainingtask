package com.qulix.losevsa.trainingtask.web.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.entity.enums.Status;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.repository.TaskRepository;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.TaskService;
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
 * The default implementation of the {@link TaskService}.
 */
public class DefaultTaskService implements TaskService {

    private static final Logger LOG = Logger.getLogger(DefaultTaskService.class);

    private static final int NAME_MAX_LENGTH = 50;

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    /**
     * Instantiates a new Default task service.
     *
     * @param employeeService the employee service
     * @param projectService  the project service
     */
    public DefaultTaskService(EmployeeService employeeService, ProjectService projectService) {
        RepositoryProvider repositoryProvider = RepositoryProvider.getInstance();
        this.taskRepository = repositoryProvider.getTaskRepository();
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @Override
    public void createTask(String name, String strProjectId, String strWorkTime, String strStartDate,
        String strEndDate, String strStatus, String strEmployeeId) {

        Integer workTime = parseInteger(strWorkTime);
        LocalDate startDate = parseDate(strStartDate);
        LocalDate endDate = parseDate(strEndDate);
        Status status = parseStatus(strStatus);
        Project project = parseProject(strProjectId);
        Employee employee = parseEmployee(strEmployeeId);

        validateValues(name, workTime, startDate, endDate, status);

        Task task = new Task();
        task.setName(name);
        task.setProject(project);
        task.setWorkTime(workTime);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setStatus(status);
        task.setEmployee(employee);

        task = taskRepository.saveTask(task);
        LOG.info(format("Successfully created task with id %d", task.getId()));
    }

    @Override
    public List<Task> getAllTasks() {
        LOG.info("Getting all tasks");
        return taskRepository.getAllTasks();
    }

    @Override
    public Task getTask(long taskId) {
        LOG.info(format("Getting task with id %d", taskId));
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        return task;
    }

    @Override
    public void updateTask(long taskId, String name, String strProjectId, String strWorkTime,
        String strStartDate, String strEndDate, String strStatus, String strEmployeeId) {

        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        Integer workTime = parseInteger(strWorkTime);
        LocalDate startDate = parseDate(strStartDate);
        LocalDate endDate = parseDate(strEndDate);
        Status status = parseStatus(strStatus);
        Project project = parseProject(strProjectId);
        Employee employee = parseEmployee(strEmployeeId);

        validateValues(name, workTime, startDate, endDate, status);

        Task newTask = new Task();
        newTask.setId(taskId);
        newTask.setName(name);
        newTask.setProject(project);
        newTask.setWorkTime(workTime);
        newTask.setStartDate(startDate);
        newTask.setEndDate(endDate);
        newTask.setStatus(status);
        newTask.setEmployee(employee);

        newTask = taskRepository.updateTask(newTask);
        LOG.info(format("Successfully updated task with id %d", newTask.getId()));
    }

    @Override
    public void deleteTask(long taskId) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        taskId = taskRepository.deleteTaskById(taskId);
        LOG.info(format("Successfully deleted task with id %d", taskId));
    }

    private void validateValues(String name, Integer workTime, LocalDate startDate,
        LocalDate endDate, Status status) {
        if (name == null || name.isBlank() || status == null) {
            throw new FieldNotFilledException(format("Required fields are empty. Name: %s, status: %s", name, status));
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new NameLengthExceededException(format("Length of name is more then %d. Name: %s", NAME_MAX_LENGTH, name));
        }

        if (workTime != null && workTime < 0) {
            throw new WorkTimeNegativeException(format("Work time is below zero. Work time: %s", workTime));
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new EndDateEarlierStartDateException(
                format("Еnd date is earlier than start date. Start date: %s, End date: %s", startDate, endDate)
            );
        }
    }

    private Status parseStatus(String strStatus) {
        try {
            Status status = null;
            if (strStatus != null && !strStatus.isBlank()) {
                status = Status.valueOf(strStatus);
            }

            return status;
        }
        catch (IllegalArgumentException e) {
            throw new StatusParseException(format("Incorrect status input. Status: %s", strStatus), e);
        }
    }

    private LocalDate parseDate(String strDate) {
        try {
            LocalDate localDate = null;
            if (strDate != null && !strDate.isBlank()) {
                localDate = LocalDate.parse(strDate);
            }

            return localDate;
        }
        catch (DateTimeParseException e) {
            throw new DateParseException(format("Incorrect date input. Date: %s", strDate), e);
        }
    }

    private Integer parseInteger(String strInt) {
        if (strInt == null || strInt.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(strInt);
        }
        catch (NumberFormatException e) {
            throw new WorkTimeParseException(format("Incorrect work time input. Work time: %s", strInt), e);
        }
    }

    private Project parseProject(String strProjectId) {
        try {
            long projectId = Long.parseLong(strProjectId);
            return projectService.getProject(projectId);
        }
        catch (NumberFormatException e) {
            throw new NoProjectException(format("Incorrect project input. Project id: %s", strProjectId), e);
        }
    }

    private Employee parseEmployee(String strEmployeeId) {
        if (strEmployeeId == null || strEmployeeId.isBlank()) {
            return null;
        }

        try {
            long employeeId = Long.parseLong(strEmployeeId);
            return employeeService.getEmployee(employeeId);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
