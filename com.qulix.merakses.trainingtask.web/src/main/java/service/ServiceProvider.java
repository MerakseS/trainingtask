package service;

import service.impl.DefaultEmployeeService;
import service.impl.DefaultProjectService;
import service.impl.DefaultTaskService;

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
     * Gets instance of service provider.
     *
     * @return the instance
     */
    public static ServiceProvider getInstance() {
        return instance;
    }

    /**
     * Gets employee service.
     *
     * @return the employee service
     */
    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    /**
     * Gets project service.
     *
     * @return the project service
     */
    public ProjectService getProjectService() {
        return projectService;
    }

    /**
     * Gets task service.
     *
     * @return the task service
     */
    public TaskService getTaskService() {
        return taskService;
    }
}
