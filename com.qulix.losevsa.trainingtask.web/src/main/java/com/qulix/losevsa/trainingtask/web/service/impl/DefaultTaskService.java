package com.qulix.losevsa.trainingtask.web.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
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
import com.qulix.losevsa.trainingtask.web.service.IncorrectInputException;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.TaskService;

/**
 * The default implementation of the {@link TaskService}
 */
public class DefaultTaskService implements TaskService {

    private static final Logger LOG = Logger.getLogger(DefaultTaskService.class);

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final int NAME_MAX_LENGTH = 50;

    private final TaskRepository taskRepository;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    /**
     * Instantiates a new Default task com.qulix.losevsa.trainingtask.web.service.
     *
     * @param employeeService the employee com.qulix.losevsa.trainingtask.web.service
     * @param projectService  the project com.qulix.losevsa.trainingtask.web.service
     */
    public DefaultTaskService(EmployeeService employeeService, ProjectService projectService) {
        RepositoryProvider repositoryProvider = RepositoryProvider.getInstance();
        this.taskRepository = repositoryProvider.getTaskRepository();
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @Override
    public Task createTask(String name, String strProjectId, String strWorkTime, String strStartDate,
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
        LOG.info("Successfully created task with id " + task.getId());

        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        LOG.info("Getting all tasks");
        return taskRepository.getAllTasks();
    }

    @Override
    public Task getTask(long taskId) {
        LOG.info("Getting task with id " + taskId);
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            LOG.error("Task with id " + taskId + " doesn't exist.");
            throw new IncorrectInputException("Задача с id " + taskId + " не существует.");
        }

        return task;
    }

    @Override
    public Task updateTask(long taskId, String name, String strProjectId, String strWorkTime,
        String strStartDate, String strEndDate, String strStatus, String strEmployeeId) {

        checkThatTaskExists(taskId);

        Integer workTime = parseInteger(strWorkTime);
        LocalDate startDate = parseDate(strStartDate);
        LocalDate endDate = parseDate(strEndDate);
        Status status = parseStatus(strStatus);
        Project project = parseProject(strProjectId);
        Employee employee = parseEmployee(strEmployeeId);

        validateValues(name, workTime, startDate, endDate, status);

        Task task = new Task();
        task.setId(taskId);
        task.setName(name);
        task.setProject(project);
        task.setWorkTime(workTime);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setStatus(status);
        task.setEmployee(employee);

        task = taskRepository.updateTask(task);
        LOG.info("Successfully updated task with id " + task.getId());

        return task;
    }

    @Override
    public long deleteTask(long taskId) {
        checkThatTaskExists(taskId);
        taskId = taskRepository.deleteTaskById(taskId);
        LOG.info("Successfully deleted task with id " + taskId);

        return taskId;
    }

    private void checkThatTaskExists(long taskId) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            LOG.error("Task with id " + taskId + " doesn't exist.");
            throw new IncorrectInputException("Задача с id " + taskId + " не существует.");
        }
    }

    private void validateValues(String name, Integer workTime, LocalDate startDate,
        LocalDate endDate, Status status) {
        if (name == null || name.isBlank() || status == null) {
            LOG.warn(format("Required fields are empty. Name: %s, status: %s", name, status));
            throw new IncorrectInputException("Введите обязательные поля.");
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new IncorrectInputException(format("Длина наименования не больше %d символов.", NAME_MAX_LENGTH));
        }

        if (workTime != null && workTime < 0) {
            LOG.warn(format("Work time is below zero. Work time: %s", workTime));
            throw new IncorrectInputException("Работа не может быть отрицательной");
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            LOG.warn(format("Еnd date is earlier than start date. Start date: %s, End date: %s", startDate, endDate));
            throw new IncorrectInputException("Дата окончания не может быть раньше даты начала");
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
            LOG.warn("Incorrect status input. Status: " + strStatus);
            throw new IncorrectInputException("Некорректный статус.");
        }
    }

    private LocalDate parseDate(String strDate) {
        try {
            LocalDate localDate = null;
            if (strDate != null && !strDate.isBlank()) {
                Date date = DATE_FORMAT.parse(strDate);
                localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            }

            return localDate;
        }
        catch (DateTimeParseException | ParseException e) {
            LOG.warn("Incorrect date input. Date: " + strDate);
            throw new IncorrectInputException("Некорректный ввод даты.");
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
            LOG.warn("Incorrect work time input. Work time: " + strInt);
            throw new IncorrectInputException("Некорректный ввод работы.");
        }
    }

    private Project parseProject(String strProjectId) {
        try {
            long projectId = Long.parseLong(strProjectId);
            return projectService.getProject(projectId);
        }
        catch (NumberFormatException e) {
            LOG.warn("Incorrect project input. Project id: " + strProjectId);
            throw new IncorrectInputException("Для добавления задачи сначала создайте проект.");
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
