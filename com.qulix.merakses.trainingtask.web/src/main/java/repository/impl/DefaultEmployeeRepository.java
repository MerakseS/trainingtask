package repository.impl;

import entity.Employee;
import repository.EmployeeRepository;
import java.util.List;

public class DefaultEmployeeRepository implements EmployeeRepository {

    @Override
    public Employee saveEmployee(Employee employee) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Employee getEmployeeById(long id) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public long deleteEmployeeById(long id) {
        return 0;
    }
}
