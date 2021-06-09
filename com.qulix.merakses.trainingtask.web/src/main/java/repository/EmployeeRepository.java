package repository;

import entity.Employee;
import java.util.List;

public interface EmployeeRepository {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(long id);

    Employee updateEmployee(Employee employee);

    long deleteEmployeeById(long id);
}
