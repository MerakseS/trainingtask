package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * The interface of business logic for working with {@link Employee}.
 */
public interface EmployeeService {

    /**
     * Creates employee.
     *
     * @param firstName  the first name
     * @param surName    the surname
     * @param patronymic the patronymic
     * @param position   the position
     *
     * @throws FieldNotFilledException if required fields are empty
     * @throws EmployeeFieldLengthExceededException if field's length is bigger than 30.
     */
    void createEmployee(String firstName, String surName, String patronymic, String position);

    /**
     * Gets all employees.
     *
     * @return the {@link List} of all employees
     * @see Employee
     */
    List<Employee> getAllEmployees();

    /**
     * Gets employee by employee id.
     *
     * @param employeeId the employee id
     * @return the employee
     * @throws NotFoundException if employee with that id doesn't exist
     */
    Employee getEmployee(long employeeId);

    /**
     * Updates employee by id.
     *
     * @param employeeId the employee id
     * @param firstName  the first name
     * @param surName    the surname
     * @param patronymic the patronymic
     * @param position   the position
     *
     * @throws NotFoundException if employee doesn't exist
     * @throws FieldNotFilledException if required fields are empty
     * @throws EmployeeFieldLengthExceededException if field's length is bigger than 30.
     */
    void updateEmployee(long employeeId, String firstName, String surName, String patronymic, String position);

    /**
     * Delete employee by id.
     *
     * @param employeeId the employee id
     * @throws NotFoundException if employee with that id doesn't exist
     */
    void deleteEmployee(long employeeId);
}
