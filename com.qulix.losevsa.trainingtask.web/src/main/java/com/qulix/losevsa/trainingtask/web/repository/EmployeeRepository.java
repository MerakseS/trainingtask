package com.qulix.losevsa.trainingtask.web.repository;

import com.qulix.losevsa.trainingtask.web.entity.Employee;

import java.util.List;

/**
 * The interface of com.qulix.losevsa.trainingtask.web.repository layer for working with {@link Employee}
 */
public interface EmployeeRepository {
    /**
     * Save employee to com.qulix.losevsa.trainingtask.web.database.
     *
     * @param employee the employee
     * @return the employee
     * @throws RepositoryException if a com.qulix.losevsa.trainingtask.web.database access error occurs
     */
    Employee saveEmployee(Employee employee);

    /**
     * Gets all employees from com.qulix.losevsa.trainingtask.web.database.
     *
     * @return the all employees
     * @throws RepositoryException if a com.qulix.losevsa.trainingtask.web.database access error occurs
     */
    List<Employee> getAllEmployees();

    /**
     * Gets employee by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the employee by id
     * @throws RepositoryException if a com.qulix.losevsa.trainingtask.web.database access error occurs
     */
    Employee getEmployeeById(long id);

    /**
     * Update employee in com.qulix.losevsa.trainingtask.web.database.
     *
     * @param employee the employee
     * @return the employee
     * @throws RepositoryException if a com.qulix.losevsa.trainingtask.web.database access error occurs
     */
    Employee updateEmployee(Employee employee);

    /**
     * Delete employee by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the long
     * @throws RepositoryException if a com.qulix.losevsa.trainingtask.web.database access error occurs
     */
    long deleteEmployeeById(long id);
}
