package service.impl;

import entity.Employee;
import entity.Project;
import entity.Task;
import entity.enums.Status;
import org.apache.log4j.Logger;
import repository.*;
import service.ProjectService;
import service.ServiceException;
import service.ServiceProvider;
import service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DefaultTaskService implements TaskService {
    private static final Logger log = Logger.getLogger(DefaultTaskService.class);

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectService projectService;

    public DefaultTaskService() {
        RepositoryProvider repositoryProvider = RepositoryProvider.getInstance();
        taskRepository = repositoryProvider.getTaskRepository();
        employeeRepository = repositoryProvider.getEmployeeRepository();
        
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        projectService = serviceProvider.getProjectService();
    }

    @Override
    public Task createTask(String name, Long projectId, String strWorkTime, String strStartDate,
                           String strEndDate, String strStatus, Long employeeId) {
        try {
            Integer workTime = parseInteger(strWorkTime);
            LocalDate startDate = parseDate(strStartDate);
            LocalDate endDate = parseDate(strEndDate);
            Status status = parseStatus(strStatus);
            Project project = projectService.getProject(projectId);
            Employee employee = employeeRepository.getEmployeeById(employeeId);
            
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
            throw new ServiceException("Некорректный ввод работы.");
        } catch (DateTimeParseException e) {
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
    public List<Task> getTasksByProject(long projectId) {
        log.info("Getting tasks list by project with id " + projectId);

        projectService.checkThatProjectExists(projectId);

        return taskRepository.getTaskListByProjectId(projectId);
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
    public Task updateTask(long taskId, String name, Long projectId, String strWorkTime,
                           String strStartDate, String strEndDate, String strStatus, Long employeeId) {
        try {
            checkThatTaskExists(taskId);

            Integer workTime = parseInteger(strWorkTime);
            LocalDate startDate = parseDate(strStartDate);
            LocalDate endDate = parseDate(strEndDate);
            Status status = parseStatus(strStatus);
            Project project = projectService.getProject(projectId);
            Employee employee = employeeRepository.getEmployeeById(employeeId);

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
        } catch (DateTimeParseException e) {
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
        if (name == null || status == null) {
            throw new ServiceException("Введите обязательные поля.");
        }

        if (name.length() > 50) {
            throw new ServiceException("Длина наименования не больше 30 символов.");
        }

        if (workTime != null && workTime < 0) {
            throw new ServiceException("Работа не может быть отрицательной");
        }

        if (endDate.isBefore(startDate)) {
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

    private LocalDate parseDate(String strDate) {
        LocalDate localDate = null;
        if (strDate != null && !strDate.isBlank()) {
            localDate = LocalDate.parse(strDate);
        }

        return localDate;
    }

    private Integer parseInteger(String strInt) {
        Integer integer = null;
        if (strInt != null && !strInt.isBlank()) {
            integer = Integer.valueOf(strInt);
        }

        return integer;
    }
}
