package service;

import service.impl.DefaultEmployeeService;
import service.impl.DefaultProjectService;
import service.impl.DefaultTaskService;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public ServiceProvider() {
        employeeService = new DefaultEmployeeService();
        projectService = new DefaultProjectService();
        taskService = new DefaultTaskService(employeeService, projectService);
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public TaskService getTaskService() {
        return taskService;
    }
}
