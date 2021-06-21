package repository;

import repository.impl.*;

public class RepositoryProvider {
    private static final RepositoryProvider instance = new RepositoryProvider();

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public RepositoryProvider() {
        employeeRepository = new DefaultEmployeeRepository();
        projectRepository = new DefaultProjectRepository();
        taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
    }

    public static RepositoryProvider getInstance() {
        return instance;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
