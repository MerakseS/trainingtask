package repository;

import repository.impl.DefaultEmployeeRepository;
import repository.impl.DefaultProjectRepository;
import repository.impl.DefaultTaskRepository;

/**
 * A singleton class that provides all repository objects.
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
