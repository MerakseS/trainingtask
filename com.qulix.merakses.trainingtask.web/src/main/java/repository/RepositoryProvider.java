package repository;

import repository.impl.*;

public class RepositoryProvider {
    private static final RepositoryProvider instance = new RepositoryProvider();

    private final EmployeeRepository employeeRepository = new DefaultEmployeeRepository();
    private final ProjectRepository projectRepository = new DefaultProjectRepository();

    public static RepositoryProvider getInstance() {
        return instance;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }
}
