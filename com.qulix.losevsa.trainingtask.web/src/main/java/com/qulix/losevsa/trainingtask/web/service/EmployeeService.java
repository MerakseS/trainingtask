package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Employee;

/**
 * The interface of business logic for working with {@link Employee}
 */
public interface EmployeeService {

    /**
     * Creates employee.
     *
     * @param firstName  the first name
     * @param surName    the sur name
     * @param patronymic the patronymic
     * @param position   the position
     * @throws IncorrectInputException if data is incorrect
     */
    void createEmployee(String firstName, String surName, String patronymic, String position);

    /**
     * Gets all employees.
     *
     * @return the {@link List} of all employees
     * @see Employee
     * @throws IncorrectInputException if there is no connection to com.qulix.losevsa.trainingtask.web.database
     */
    List<Employee> getAllEmployees();

    /**
     * Gets employee by employee id.
     *
     * @param employeeId the employee id
     * @return the employee
     * @throws IncorrectInputException if employee with that id doesn't exists
     */
    Employee getEmployee(long employeeId);

    /**
     * Updates employee.
     *
     * @param employeeId the employee id
     * @param firstName  the first name
     * @param surName    the sur name
     * @param patronymic the patronymic
     * @param position   the position
     * @throws IncorrectInputException if data is incorrect
     */
    void updateEmployee(long employeeId, String firstName, String surName, String patronymic, String position);

    /**
     * Delete employee by id.
     *
     * @param employeeId the employee id
     * @throws IncorrectInputException if employee with that id doesn't exists
     */
    void deleteEmployee(long employeeId);
}
