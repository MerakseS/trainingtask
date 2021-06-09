package repository;

import repository.impl.*;

public class RepositoryProvider {
    private static final RepositoryProvider instance = new RepositoryProvider();

    private static final EmployeeRepository employeeRepository = new DefaultEmployeeRepository();

    public static RepositoryProvider getInstance() {
        return instance;
    }

    public static EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }
}
