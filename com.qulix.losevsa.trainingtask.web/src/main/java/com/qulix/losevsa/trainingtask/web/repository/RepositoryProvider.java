package com.qulix.losevsa.trainingtask.web.repository;

import com.qulix.losevsa.trainingtask.web.repository.impl.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.impl.DefaultProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.impl.DefaultTaskRepository;

/**
 * A singleton class that provides all com.qulix.losevsa.trainingtask.web.repository objects.
 */
public class RepositoryProvider {

    private static final RepositoryProvider instance = new RepositoryProvider();

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
        return instance;
    }

    /**
     * Gets employee com.qulix.losevsa.trainingtask.web.repository.
     *
     * @return the employee com.qulix.losevsa.trainingtask.web.repository
     */
    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    /**
     * Gets project com.qulix.losevsa.trainingtask.web.repository.
     *
     * @return the project com.qulix.losevsa.trainingtask.web.repository
     */
    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    /**
     * Gets task com.qulix.losevsa.trainingtask.web.repository.
     *
     * @return the task com.qulix.losevsa.trainingtask.web.repository
     */
    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
