package com.qulix.losevsa.trainingtask.web.service;

import com.qulix.losevsa.trainingtask.web.service.impl.DefaultEmployeeService;
import com.qulix.losevsa.trainingtask.web.service.impl.DefaultProjectService;
import com.qulix.losevsa.trainingtask.web.service.impl.DefaultTaskService;

/**
 * A singleton class that provides all services.
 */
public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final TaskService taskService;

    /**
     * Instantiates a new Service provider.
     */
    public ServiceProvider() {
        employeeService = new DefaultEmployeeService();
        projectService = new DefaultProjectService();
        taskService = new DefaultTaskService(employeeService, projectService);
    }

    /**
     * Gets instance of com.qulix.losevsa.trainingtask.web.service provider.
     *
     * @return the instance
     */
    public static ServiceProvider getInstance() {
        return instance;
    }

    /**
     * Gets employee com.qulix.losevsa.trainingtask.web.service.
     *
     * @return the employee com.qulix.losevsa.trainingtask.web.service
     */
    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    /**
     * Gets project com.qulix.losevsa.trainingtask.web.service.
     *
     * @return the project com.qulix.losevsa.trainingtask.web.service
     */
    public ProjectService getProjectService() {
        return projectService;
    }

    /**
     * Gets task com.qulix.losevsa.trainingtask.web.service.
     *
     * @return the task com.qulix.losevsa.trainingtask.web.service
     */
    public TaskService getTaskService() {
        return taskService;
    }
}
