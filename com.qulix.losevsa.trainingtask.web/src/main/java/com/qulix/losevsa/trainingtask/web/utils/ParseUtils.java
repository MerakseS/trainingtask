package com.qulix.losevsa.trainingtask.web.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import static java.lang.String.format;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.TaskStatus;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.DateParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeIdParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.NoProjectException;
import com.qulix.losevsa.trainingtask.web.service.exception.TaskStatusParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeParseException;

/**
 * Utils for parsing task fields types.
 */
public class ParseUtils {

    private final Service<Employee> employeeService;
    private final Service<Project> projectService;

    /**
     * Instantiates a new Parse utils.
     *
     * @param employeeService the employee service 
     * @param projectService the project service
     */
    public ParseUtils(Service<Employee> employeeService, Service<Project> projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    /**
     * Parses the string argument as a {@link TaskStatus}.
     *
     * @param s a {@code String} containing the {@link TaskStatus} representation to be parsed 
     * @return the {@link TaskStatus} represented by the argument or null. 
     * @throws TaskStatusParseException if the string does not contain a parsable {@link TaskStatus}.
     */
    public TaskStatus parseTaskStatus(String s) {
        try {
            TaskStatus taskStatus = null;
            if (s != null && !s.isBlank()) {
                taskStatus = TaskStatus.valueOf(s);
            }

            return taskStatus;
        }
        catch (IllegalArgumentException e) {
            throw new TaskStatusParseException(format("Incorrect status input. Task Status: %s", s), e);
        }
    }

    /**
     * Parses the string argument as a {@link LocalDate} such as 2007-12-03.
     *
     * @param s a {@code String} containing the {@link LocalDate} representation to be parsed 
     * @return the {@link LocalDate} represented by the argument. 
     * @throws DateParseException if the string does not contain a parsable {@link LocalDate}.
     */
    public LocalDate parseDate(String s) {
        try {
            LocalDate localDate = null;
            if (s != null && !s.isBlank()) {
                localDate = LocalDate.parse(s);
            }

            return localDate;
        }
        catch (DateTimeParseException e) {
            throw new DateParseException(format("Incorrect date input. Date: %s", s), e);
        }
    }

    /**
     * Parses the string argument as a {@link Integer}.
     *
     * @param s a {@code String} containing the {@link Integer} representation to be parsed 
     * @return the {@link Integer} represented by the argument or null. 
     * @throws WorkTimeParseException if the string does not contain a parsable {@link Integer}.
     */
    public Integer parseInteger(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            throw new WorkTimeParseException(format("Incorrect work time input. Work time: %s", s), e);
        }
    }

    /**
     * Parses id string argument as a {@link Project}.
     *
     * @param s a {@code String} containing the {@link Project} id representation to be parsed.
     * @return the {@link Project} represented by the argument id. 
     * @throws NoProjectException if the string does not contain a parsable {@link Project} id.
     */
    public Project parseProject(String s) {
        try {
            long projectId = Long.parseLong(s);
            return projectService.get(projectId);
        }
        catch (NumberFormatException e) {
            throw new NoProjectException(format("Incorrect project input. Project id: %s", s), e);
        }
    }

    /**
     * Parses id string argument as a {@link Employee}.
     *
     * @param s a {@code String} containing the {@link Employee} id representation to be parsed.
     * @return the {@link Employee} represented by the argument id.
     * @throws EmployeeIdParseException if the string does not contain a parsable {@link Employee} id.
     */
    public Employee parseEmployee(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }

        try {
            long employeeId = Long.parseLong(s);
            return employeeService.get(employeeId);
        }
        catch (NumberFormatException e) {
            throw new EmployeeIdParseException(format("Incorrect employee id input. Employee id: %s", s), e);
        }
    }
}
