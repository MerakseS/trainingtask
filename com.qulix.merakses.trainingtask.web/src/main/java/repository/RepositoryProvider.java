package repository;

import repository.impl.*;

public class RepositoryProvider {
    private static final RepositoryProvider instance = new RepositoryProvider();

    private final EmployeeRepository employeeRepository = new DefaultEmployeeRepository();

    public static RepositoryProvider getInstance() {
        return instance;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }
}
