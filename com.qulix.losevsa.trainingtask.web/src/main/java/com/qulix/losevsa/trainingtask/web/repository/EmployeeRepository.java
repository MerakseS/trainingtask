package com.qulix.losevsa.trainingtask.web.repository;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Employee;

/**
 * The interface of repository layer for working with {@link Employee}
 */
public interface EmployeeRepository {

    /**
     * Save employee to database.
     *
     * @param employee the employee
     * @return the employee
     * @throws QueryExecutionException if a database access error occurs
     */
    Employee saveEmployee(Employee employee);

    /**
     * Gets all employees from database.
     *
     * @return the all employees
     * @throws QueryExecutionException if a database access error occurs
     */
    List<Employee> getAllEmployees();

    /**
     * Gets employee by id from database.
     *
     * @param id the id
     * @return the employee by id
     * @throws QueryExecutionException if a database access error occurs
     */
    Employee getEmployeeById(long id);

    /**
     * Update employee in database.
     *
     * @param employee the employee
     * @return the employee
     * @throws QueryExecutionException if a database access error occurs
     */
    Employee updateEmployee(Employee employee);

    /**
     * Delete employee by id from database.
     *
     * @param id the id
     * @return the long
     * @throws QueryExecutionException if a database access error occurs
     */
    long deleteEmployeeById(long id);
}
