package com.qulix.losevsa.trainingtask.web.repository;

import com.qulix.losevsa.trainingtask.web.repository.impl.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.impl.DefaultProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.impl.DefaultTaskRepository;

/**
 * A singleton class that provides all repository objects.
 */
public class RepositoryProvider {

    private static final RepositoryProvider INSTANCE = new RepositoryProvider();

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    /**
     * Instantiates a new Repository provider.
     */
    public RepositoryProvider() {
        employeeRepository = new DefaultEmployeeRepository();
        projectRepository = new DefaultProjectRepository();
        taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RepositoryProvider getInstance() {
        return INSTANCE;
    }

    /**
     * Gets employee repository.
     *
     * @return the employee repository
     */
    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    /**
     * Gets project repository.
     *
     * @return the project repository
     */
    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    /**
     * Gets task repository.
     *
     * @return the task repository
     */
    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
