package repository;

import entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeRepository {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees() throws SQLException;

    Employee getEmployeeById(long id);

    Employee updateEmployee(Employee employee);

    long deleteEmployeeById(long id);
}
