package com.qulix.losevsa.trainingtask.web.service.impl;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.entity.enums.Status;
import org.apache.log4j.Logger;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryException;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.repository.TaskRepository;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.ServiceException;
import com.qulix.losevsa.trainingtask.web.service.TaskService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

/**
 * The default implementation of the {@link TaskService}
 */
public class DefaultTaskService implements TaskService {
    private static final Logger log = Logger.getLogger(DefaultTaskService.class);

    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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
        try {
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
            log.info("Successfully created task with id " + task.getId());

            return task;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("Для добавления задачи сначала создайте проект.");
        } catch (DateTimeParseException | ParseException e) {
            throw new ServiceException("Некорректный ввод даты.");
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Некорректный статус.");
        }
    }

    @Override
    public List<Task> getAllTasks() {
        try {
            log.info("Getting all tasks");
            return taskRepository.getAllTasks();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Task getTask(long taskId) {
        try {
            log.info("Getting task with id " + taskId);
            Task task = taskRepository.getTaskById(taskId);
            if (task == null) {
                log.error("Task with id " + taskId + " doesn't exist.");
                throw new ServiceException("Задача с id " + taskId + " не существует.");
            }

            return task;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Task updateTask(long taskId, String name, String strProjectId, String strWorkTime,
                           String strStartDate, String strEndDate, String strStatus, String strEmployeeId) {
        try {
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
            log.info("Successfully updated task with id " + task.getId());

            return task;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("Некорректный ввод работы.");
        } catch (DateTimeParseException | ParseException e) {
            throw new ServiceException("Некорректный ввод даты.");
        } catch (IllegalArgumentException e) {
            throw new ServiceException("Некорректный статус.");
        }
    }

    @Override
    public long deleteTask(long taskId) {
        try {
            checkThatTaskExists(taskId);
            taskId = taskRepository.deleteTaskById(taskId);
            log.info("Successfully deleted task with id " + taskId);

            return taskId;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkThatTaskExists(long taskId) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            log.error("Task with id " + taskId + " doesn't exist.");
            throw new ServiceException("Задача с id " + taskId + " не существует.");
        }
    }

    private void validateValues(String name, Integer workTime, LocalDate startDate,
                                LocalDate endDate, Status status) {
        if (name == null || name.isBlank() || status == null) {
            throw new ServiceException("Введите обязательные поля.");
        }

        if (name.length() > 50) {
            throw new ServiceException("Длина наименования не больше 50 символов.");
        }

        if (workTime != null && workTime < 0) {
            throw new ServiceException("Работа не может быть отрицательной");
        }

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new ServiceException("Дата окончания не может быть раньше даты начала");
        }

    }

    private Status parseStatus(String strStatus) {
        Status status = null;
        if (strStatus != null && !strStatus.isBlank()) {
            status = Status.valueOf(strStatus);
        }

        return status;
    }

    private LocalDate parseDate(String strDate) throws ParseException {
        LocalDate localDate = null;
        if (strDate != null && !strDate.isBlank()) {
            Date date = formatter.parse(strDate);
            localDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        return localDate;
    }

    private Integer parseInteger(String strInt) {
        if (strInt == null || strInt.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(strInt);
        } catch (NumberFormatException e) {
            throw new ServiceException("Некорректный ввод работы.");
        }
    }

    private Project parseProject(String strProjectId) {
        long projectId = Long.parseLong(strProjectId);
        return projectService.getProject(projectId);
    }

    private Employee parseEmployee(String strEmployeeId) {
        if (strEmployeeId == null || strEmployeeId.isBlank()) {
            return null;
        }

        try {
            long employeeId = Long.parseLong(strEmployeeId);
            return employeeService.getEmployee(employeeId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
