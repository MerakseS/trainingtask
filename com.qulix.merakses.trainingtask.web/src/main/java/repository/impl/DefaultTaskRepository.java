package repository.impl;

import database.DatabaseConnection;
import entity.Project;
import entity.Task;
import entity.enums.Status;
import repository.EmployeeRepository;
import repository.ProjectRepository;
import repository.RepositoryException;
import repository.TaskRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DefaultTaskRepository implements TaskRepository {

    private static final String TASK_ID_COLUMN_NAME = "T_ID";
    private static final String TASK_STATUS_COLUMN_NAME = "T_STATUS";
    private static final String TASK_NAME_COLUMN_NAME = "T_NAME";
    private static final String TASK_PROJECT_COLUMN_NAME = "T_PROJECT";
    private static final String TASK_WORK_TIME_COLUMN_NAME = "T_WORK_TIME";
    private static final String TASK_START_DATE_COLUMN_NAME = "T_START_DATE";
    private static final String TASK_END_DATE_COLUMN_NAME = "T_END_DATE";
    private static final String TASK_EXECUTOR_COLUMN_NAME = "T_EXECUTOR";

    private static final String SAVE_TASK_QUERY = "INSERT INTO TASK (T_STATUS, T_NAME, T_PROJECT, " +
            "T_WORK_TIME, T_START_DATE, T_END_DATE, T_EXECUTOR) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_TASKS_QUERY = "SELECT * FROM TASK";
    private static final String GET_TASKS_BY_PROJECT_ID_QUERY = "SELECT * FROM TASK WHERE T_PROJECT = ?";
    private static final String GET_TASK_BY_ID_QUERY = "SELECT * FROM TASK WHERE T_ID = ?";
    private static final String UPDATE_TASK_QUERY = "UPDATE TASK SET T_STATUS = ?, T_NAME = ?, T_PROJECT = ?, " +
            "T_WORK_TIME = ?, T_START_DATE = ?, T_END_DATE = ?, T_EXECUTOR = ? WHERE T_ID = ?";
    private static final String DELETE_TASK_QUERY = "DELETE FROM TASK WHERE T_ID = ?";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public DefaultTaskRepository(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Task saveTask(Task task) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_TASK_QUERY, RETURN_GENERATED_KEYS)) {

            setValuesToStatement(task, statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't save task. No rows affected. Task: %s", task));
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException(format("Can't save task. No ID obtained. Task: %s", task));
                }
            }

            return task;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_TASKS_QUERY)) {

            try (ResultSet result = statement.executeQuery()) {
                List<Task> taskList = new ArrayList<>();
                while (result.next()) {
                    Task task = getTaskByResultSet(result);
                    taskList.add(task);
                }

                return taskList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Task> getTaskListByProjectId(long projectId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_TASKS_BY_PROJECT_ID_QUERY)) {

            statement.setLong(1, projectId);

            try (ResultSet result = statement.executeQuery()) {
                List<Task> taskList = new ArrayList<>();
                while (result.next()) {
                    Task task = getTaskByResultSet(result);
                    taskList.add(task);
                }

                return taskList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Task getTaskById(long taskId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TASK_BY_ID_QUERY)) {

            statement.setLong(1, taskId);

            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? getTaskByResultSet(result) : null;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Task updateTask(Task task) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_QUERY)) {

            setValuesToStatement(task, statement);
            statement.setLong(8, task.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't update task. No rows affected. Task: %s", task));
            }

            return task;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public long deleteTaskById(long taskId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TASK_QUERY)) {

            statement.setLong(1, taskId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't delete task. No rows affected. Task's id: %s", taskId));
            }

            return taskId;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void setValuesToStatement(Task task, PreparedStatement statement) throws SQLException {
        statement.setString(1, task.getStatus().name());
        statement.setString(2, task.getName());
        statement.setLong(3, task.getProject().getId());

        if (task.getWorkTime() != null) {
            statement.setInt(4, task.getWorkTime());
        } else {
            statement.setNull(4, Types.INTEGER);
        }

        if (task.getStartDate() != null) {
            statement.setDate(5, Date.valueOf(task.getStartDate()));
        } else {
            statement.setNull(5, Types.DATE);
        }

        if (task.getEndDate() != null) {
            statement.setDate(6, Date.valueOf(task.getEndDate()));
        } else {
            statement.setNull(6, Types.DATE);
        }

        if (task.getEmployee() != null) {
            statement.setLong(7, task.getEmployee().getId());
        } else {
            statement.setNull(7, Types.BIGINT);
        }
    }

    private Task getTaskByResultSet(ResultSet result) throws SQLException {
        Task task = new Task();
        task.setId(result.getLong(TASK_ID_COLUMN_NAME));
        task.setStatus(Status.valueOf(result.getString(TASK_STATUS_COLUMN_NAME)));
        task.setName(result.getString(TASK_NAME_COLUMN_NAME));

        long projectId = result.getLong(TASK_PROJECT_COLUMN_NAME);
        Project project = projectRepository.getProjectById(projectId);
        task.setProject(project);

        int workTime = result.getInt(TASK_WORK_TIME_COLUMN_NAME);
        task.setWorkTime(result.wasNull() ? null : workTime);

        Date sqlStartDate = result.getDate(TASK_START_DATE_COLUMN_NAME);
        task.setStartDate(sqlStartDate != null ? sqlStartDate.toLocalDate() : null);

        Date sqlEndDate = result.getDate(TASK_END_DATE_COLUMN_NAME);
        task.setEndDate(sqlEndDate != null ? sqlEndDate.toLocalDate() : null);

        long employeeId = result.getLong(TASK_EXECUTOR_COLUMN_NAME);
        task.setEmployee(result.wasNull() ? null : employeeRepository.getEmployeeById(employeeId));

        return task;
    }
}
