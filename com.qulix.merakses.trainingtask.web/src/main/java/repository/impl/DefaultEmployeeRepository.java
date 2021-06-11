package repository.impl;

import database.DatabaseConnection;
import entity.Employee;
import repository.EmployeeRepository;
import repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultEmployeeRepository implements EmployeeRepository {

    private static final String EMPLOYEE_ID_COLUMN_NAME = "E_ID";
    private static final String EMPLOYEE_FIRST_NAME_COLUMN_NAME = "E_FIRST_NAME";
    private static final String EMPLOYEE_SURNAME_COLUMN_NAME = "E_SURNAME";
    private static final String EMPLOYEE_PATRONYMIC_COLUMN_NAME = "E_PATRONYMIC";

    private static final String GET_ALL_EMPLOYEES_QUERY = "SELECT * FROM EMPLOYEE";
    private static final String GET_EMPLOYEE_BY_ID_QUERY = "SELECT * FROM EMPLOYEE WHERE E_ID = ?";

    @Override
    public Employee saveEmployee(Employee employee) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_EMPLOYEES_QUERY)) {
            List<Employee> employeeList = new ArrayList<>();

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Employee employee = new Employee();
                    employee.setId(result.getLong(EMPLOYEE_ID_COLUMN_NAME));
                    employee.setFirstName(result.getString(EMPLOYEE_FIRST_NAME_COLUMN_NAME));
                    employee.setSurName(result.getString(EMPLOYEE_SURNAME_COLUMN_NAME));
                    employee.setPatronymic(result.getString(EMPLOYEE_PATRONYMIC_COLUMN_NAME));
                    employeeList.add(employee);
                }
            }

            return employeeList;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Employee getEmployeeById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEE_BY_ID_QUERY)) {

            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Employee employee = new Employee();
                    employee.setId(result.getLong(EMPLOYEE_ID_COLUMN_NAME));
                    employee.setFirstName(result.getString(EMPLOYEE_FIRST_NAME_COLUMN_NAME));
                    employee.setSurName(result.getString(EMPLOYEE_SURNAME_COLUMN_NAME));
                    employee.setPatronymic(result.getString(EMPLOYEE_PATRONYMIC_COLUMN_NAME));
                    return employee;
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
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
