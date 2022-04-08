package com.qulix.losevsa.trainingtask.web.repository;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;

/**
 * A singleton class that provides all repository objects.
 */
public class RepositoryProvider {

    private static final RepositoryProvider INSTANCE = new RepositoryProvider();

    private final Repository<Employee> employeeRepository;
    private final Repository<Project> projectRepository;
    private final Repository<Task> taskRepository;

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
    public Repository<Employee> getEmployeeRepository() {
        return employeeRepository;
    }

    /**
     * Gets project repository.
     *
     * @return the project repository
     */
    public Repository<Project> getProjectRepository() {
        return projectRepository;
    }

    /**
     * Gets task repository.
     *
     * @return the task repository
     */
    public Repository<Task> getTaskRepository() {
        return taskRepository;
    }
}
