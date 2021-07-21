package repository;

import entity.Employee;

import java.util.List;

/**
 * The interface of repository layer for working with {@link Employee}
 */
public interface EmployeeRepository {
    /**
     * Save employee to database.
     *
     * @param employee the employee
     * @return the employee
     * @throws RepositoryException if a database access error occurs
     */
    Employee saveEmployee(Employee employee);

    /**
     * Gets all employees from database.
     *
     * @return the all employees
     * @throws RepositoryException if a database access error occurs
     */
    List<Employee> getAllEmployees();

    /**
     * Gets employee by id from database.
     *
     * @param id the id
     * @return the employee by id
     * @throws RepositoryException if a database access error occurs
     */
    Employee getEmployeeById(long id);

    /**
     * Update employee in database.
     *
     * @param employee the employee
     * @return the employee
     * @throws RepositoryException if a database access error occurs
     */
    Employee updateEmployee(Employee employee);

    /**
     * Delete employee by id from database.
     *
     * @param id the id
     * @return the long
     * @throws RepositoryException if a database access error occurs
     */
    long deleteEmployeeById(long id);
}
