package com.qulix.losevsa.trainingtask.web.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.dto.TaskDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.entity.TaskStatus;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.exception.DateParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.EndDateEarlierStartDateException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NoProjectException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;
import com.qulix.losevsa.trainingtask.web.service.exception.TaskStatusParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeNegativeException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeParseException;

/**
 * The default {@link Task} implementation of {@link Service}.
 */
public class DefaultTaskService implements Service<Task, TaskDto> {

    private static final Logger LOG = Logger.getLogger(DefaultTaskService.class);

    private static final int NAME_MAX_LENGTH = 50;

    private final Repository<Task> taskRepository;
    private final Service<Employee, EmployeeDto> employeeService;
    private final Service<Project, ProjectDto> projectService;

    /**
     * Instantiates a new Default task service.
     *
     * @param employeeService the employee service
     * @param projectService  the project service
     * @param taskRepository  the repository for {@link Task}
     */
    public DefaultTaskService(Service<Employee, EmployeeDto> employeeService, Service<Project, ProjectDto> projectService, Repository<Task> taskRepository) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(TaskDto taskDto) {
        Integer workTime = parseInteger(taskDto.getWorkTime());
        LocalDate startDate = parseDate(taskDto.getStartDate());
        LocalDate endDate = parseDate(taskDto.getEndDate());
        TaskStatus taskStatus = parseTaskStatus(taskDto.getTaskStatus());
        Project project = parseProject(taskDto.getProjectId());
        Employee employee = parseEmployee(taskDto.getEmployeeId());

        validateValues(taskDto.getName(), workTime, startDate, endDate, taskStatus);

        Task task = new Task();
        task.setName(taskDto.getName());
        task.setProject(project);
        task.setWorkTime(workTime);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setTaskStatus(taskStatus);
        task.setEmployee(employee);

        task = taskRepository.save(task);
        LOG.info(format("Successfully created task with id %d", task.getId()));
    }

    @Override
    public List<Task> getAll() {
        LOG.info("Getting all tasks");
        return taskRepository.getAll();
    }

    @Override
    public Task get(long taskId) {
        LOG.info(format("Getting task with id %d", taskId));
        Task task = taskRepository.getById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        return task;
    }

    @Override
    public void update(long taskId, TaskDto taskDto) {

        Task task = taskRepository.getById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        Integer workTime = parseInteger(taskDto.getWorkTime());
        LocalDate startDate = parseDate(taskDto.getStartDate());
        LocalDate endDate = parseDate(taskDto.getEndDate());
        TaskStatus taskStatus = parseTaskStatus(taskDto.getTaskStatus());
        Project project = parseProject(taskDto.getProjectId());
        Employee employee = parseEmployee(taskDto.getEmployeeId());

        validateValues(taskDto.getName(), workTime, startDate, endDate, taskStatus);

        Task newTask = new Task();
        newTask.setId(taskId);
        newTask.setName(taskDto.getName());
        newTask.setProject(project);
        newTask.setWorkTime(workTime);
        newTask.setStartDate(startDate);
        newTask.setEndDate(endDate);
        newTask.setTaskStatus(taskStatus);
        newTask.setEmployee(employee);

        newTask = taskRepository.update(newTask);
        LOG.info(format("Successfully updated task with id %d", newTask.getId()));
    }

    @Override
    public void delete(long taskId) {
        Task task = taskRepository.getById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        taskId = taskRepository.deleteById(taskId);
        LOG.info(format("Successfully deleted task with id %d", taskId));
    }

    private void validateValues(String name, Integer workTime, LocalDate startDate,
        LocalDate endDate, TaskStatus taskStatus) {
        if (name == null || name.isBlank() || taskStatus == null) {
            throw new FieldNotFilledException(format("Required fields are empty. Name: %s, task status: %s", name, taskStatus));
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

    private TaskStatus parseTaskStatus(String strTaskStatus) {
        try {
            TaskStatus taskStatus = null;
            if (strTaskStatus != null && !strTaskStatus.isBlank()) {
                taskStatus = TaskStatus.valueOf(strTaskStatus);
            }

            return taskStatus;
        }
        catch (IllegalArgumentException e) {
            throw new TaskStatusParseException(format("Incorrect status input. Task Status: %s", strTaskStatus), e);
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
            return projectService.get(projectId);
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
            return employeeService.get(employeeId);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
