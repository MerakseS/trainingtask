package service;

import service.impl.*;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final EmployeeService employeeService = new DefaultEmployeeService();

    public static ServiceProvider getInstance() {
        return instance;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }
}
