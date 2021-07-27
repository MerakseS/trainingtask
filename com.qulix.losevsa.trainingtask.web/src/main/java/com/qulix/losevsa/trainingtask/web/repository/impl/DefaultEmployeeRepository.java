package com.qulix.losevsa.trainingtask.web.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.String.format;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.database.DatabaseConnection;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.QueryExecutionException;
import com.qulix.losevsa.trainingtask.web.repository.EmployeeRepository;

/**
 * The default implementation of {@link EmployeeRepository}
 */
public class DefaultEmployeeRepository implements EmployeeRepository {

    private static final Logger LOG = Logger.getLogger(DefaultEmployeeRepository.class);

    private static final String EMPLOYEE_ID_COLUMN_NAME = "ID";
    private static final String EMPLOYEE_FIRST_NAME_COLUMN_NAME = "FIRST_NAME";
    private static final String EMPLOYEE_SURNAME_COLUMN_NAME = "SURNAME";
    private static final String EMPLOYEE_PATRONYMIC_COLUMN_NAME = "PATRONYMIC";
    private static final String EMPLOYEE_POSITION_COLUMN_NAME = "POSITION";

    private static final String SAVE_EMPLOYEE_QUERY =
        "INSERT INTO EMPLOYEE (FIRST_NAME, SURNAME, PATRONYMIC, POSITION) VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_EMPLOYEES_QUERY = "SELECT * FROM EMPLOYEE";
    private static final String GET_EMPLOYEE_BY_ID_QUERY = "SELECT * FROM EMPLOYEE WHERE ID = ?";
    private static final String UPDATE_EMPLOYEE_QUERY =
        "UPDATE EMPLOYEE SET FIRST_NAME = ?, SURNAME = ?, PATRONYMIC = ?, POSITION = ? WHERE ID = ?";
    private static final String DELETE_EMPLOYEE_QUERY = "DELETE FROM EMPLOYEE WHERE ID = ?";

    @Override
    public Employee saveEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_EMPLOYEE_QUERY, RETURN_GENERATED_KEYS)) {

            setValuesToStatement(employee, statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't save employee. No rows affected. Employee: %s", employee));
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                employee.setId(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException(format("Can't save employee. No ID obtained. Employee: %s", employee));
            }

            return employee;
        }
        catch (SQLException e) {
            LOG.error(format("Can't save employee cause: %s. Employee: %s", e.getMessage(), employee));
            throw new QueryExecutionException(e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_EMPLOYEES_QUERY)) {
            List<Employee> employeeList = new ArrayList<>();

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Employee employee = getEmployeeByResultSet(result);
                employeeList.add(employee);
            }

            return employeeList;
        }
        catch (SQLException e) {
            LOG.error(format("Can't get all employees cause: %s", e.getMessage()));
            throw new QueryExecutionException(e);
        }
    }

    @Override
    public Employee getEmployeeById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEE_BY_ID_QUERY)) {

            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            return result.next() ? getEmployeeByResultSet(result) : null;
        }
        catch (SQLException e) {
            LOG.error(format("Can't get employee cause: %s. Employee: %d", e.getMessage(), id));
            throw new QueryExecutionException(e);
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE_QUERY)) {

            setValuesToStatement(employee, statement);
            statement.setLong(5, employee.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't update employee. No rows affected. Employee: %s", employee));
            }

            return employee;
        }
        catch (SQLException e) {
            LOG.error(format("Can't update employee cause: %s. Employee: %s", e.getMessage(), employee));
            throw new QueryExecutionException(e);
        }
    }

    @Override
    public long deleteEmployeeById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_QUERY)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't update employee. No rows affected. Employee's id: %s", id));
            }

            return id;
        }
        catch (SQLException e) {
            LOG.error(format("Can't delete employee cause: %s. Employee: %s", e.getMessage(), id));
            throw new QueryExecutionException(e);
        }
    }

    private void setValuesToStatement(Employee employee, PreparedStatement statement) throws SQLException {
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getSurName());
        statement.setString(3, employee.getPatronymic());
        statement.setString(4, employee.getPosition());
    }

    private Employee getEmployeeByResultSet(ResultSet result) throws SQLException {
        Employee employee = new Employee();
        employee.setId(result.getLong(EMPLOYEE_ID_COLUMN_NAME));
        employee.setFirstName(result.getString(EMPLOYEE_FIRST_NAME_COLUMN_NAME));
        employee.setSurName(result.getString(EMPLOYEE_SURNAME_COLUMN_NAME));
        employee.setPatronymic(result.getString(EMPLOYEE_PATRONYMIC_COLUMN_NAME));
        employee.setPosition(result.getString(EMPLOYEE_POSITION_COLUMN_NAME));

        return employee;
    }
}
