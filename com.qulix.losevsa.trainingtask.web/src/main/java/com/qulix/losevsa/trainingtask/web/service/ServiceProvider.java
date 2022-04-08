package com.qulix.losevsa.trainingtask.web.service;

import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.dto.TaskDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;

/**
 * A singleton class that provides all services.
 */
public class ServiceProvider {

    private static final ServiceProvider INSTANCE = new ServiceProvider();

    private final Service<Employee, EmployeeDto> employeeService;
    private final Service<Project, ProjectDto> projectService;
    private final Service<Task, TaskDto> taskService;

    /**
     * Instantiates a new Service provider.
     */
    public ServiceProvider() {
        employeeService = new DefaultEmployeeService();
        projectService = new DefaultProjectService();
        taskService = new DefaultTaskService(employeeService, projectService);
    }

    /**
     * Gets instance of service provider.
     *
     * @return the instance
     */
    public static ServiceProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Gets employee service.
     *
     * @return the employee service
     */
    public Service<Employee, EmployeeDto> getEmployeeService() {
        return employeeService;
    }

    /**
     * Gets project service.
     *
     * @return the project service
     */
    public Service<Project, ProjectDto> getProjectService() {
        return projectService;
    }

    /**
     * Gets task service.
     *
     * @return the task service
     */
    public Service<Task, TaskDto> getTaskService() {
        return taskService;
    }
}
