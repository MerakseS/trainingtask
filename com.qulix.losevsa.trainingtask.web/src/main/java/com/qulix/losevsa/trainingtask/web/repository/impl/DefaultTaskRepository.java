package com.qulix.losevsa.trainingtask.web.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import static java.lang.String.format;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.database.DatabaseConnection;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.entity.enums.Status;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryException;
import com.qulix.losevsa.trainingtask.web.repository.EmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.ProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.TaskRepository;

/**
 * The default implementation of {@link Task}
 */
public class DefaultTaskRepository implements TaskRepository {
    private static final Logger LOG = Logger.getLogger(DefaultTaskRepository.class);

    private static final String TASK_ID_COLUMN_NAME = "ID";
    private static final String TASK_STATUS_COLUMN_NAME = "STATUS";
    private static final String TASK_NAME_COLUMN_NAME = "NAME";
    private static final String TASK_PROJECT_COLUMN_NAME = "PROJECT";
    private static final String TASK_WORK_TIME_COLUMN_NAME = "WORK_TIME";
    private static final String TASK_START_DATE_COLUMN_NAME = "STARt_DATE";
    private static final String TASK_END_DATE_COLUMN_NAME = "END_DATE";
    private static final String TASK_EXECUTOR_COLUMN_NAME = "EXECUTOR";

    private static final String SAVE_TASK_QUERY = "INSERT INTO TASK (STATUS, NAME, PROJECT, " +
        "WORK_TIME, START_DATE, END_DATE, EXECUTOR) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_TASKS_QUERY = "SELECT * FROM TASK";
    private static final String GET_TASKS_BY_PROJECT_ID_QUERY = "SELECT * FROM TASK WHERE PROJECT = ?";
    private static final String GET_TASK_BY_ID_QUERY = "SELECT * FROM TASK WHERE ID = ?";
    private static final String UPDATE_TASK_QUERY = "UPDATE TASK SET STATUS = ?, NAME = ?, PROJECT = ?, " +
        "WORK_TIME = ?, START_DATE = ?, END_DATE = ?, EXECUTOR = ? WHERE ID = ?";
    private static final String DELETE_TASK_QUERY = "DELETE FROM TASK WHERE ID = ?";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    /**
     * Instantiates a new Default task com.qulix.losevsa.trainingtask.web.repository.
     *
     * @param employeeRepository the employee com.qulix.losevsa.trainingtask.web.repository
     * @param projectRepository  the project com.qulix.losevsa.trainingtask.web.repository
     */
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

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException(format("Can't save task. No ID obtained. Task: %s", task));
            }

            return task;
        }
        catch (SQLException e) {
            LOG.error(format("Can't save task cause: %s. Employee: %s", e.getMessage(), task));
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_TASKS_QUERY)) {

            ResultSet result = statement.executeQuery();
            List<Task> taskList = new ArrayList<>();
            while (result.next()) {
                Task task = getTaskByResultSet(result);
                taskList.add(task);
            }

            return taskList;
        }
        catch (SQLException e) {
            LOG.error(format("Can't get all tasks cause: %s", e.getMessage()));
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Task> getTaskListByProjectId(long projectId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                 connection.prepareStatement(GET_TASKS_BY_PROJECT_ID_QUERY)) {

            statement.setLong(1, projectId);

            ResultSet result = statement.executeQuery();
            List<Task> taskList = new ArrayList<>();
            while (result.next()) {
                Task task = getTaskByResultSet(result);
                taskList.add(task);
            }

            return taskList;
        }
        catch (SQLException e) {
            LOG.error(format("Can't get task list cause: %s. Project id: %d", e.getMessage(), projectId));
            throw new RepositoryException(e);
        }
    }

    @Override
    public Task getTaskById(long taskId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_TASK_BY_ID_QUERY)) {

            statement.setLong(1, taskId);

            ResultSet result = statement.executeQuery();
            return result.next() ? getTaskByResultSet(result) : null;
        }
        catch (SQLException e) {
            LOG.error(format("Can't get task cause: %s. Employee: %d", e.getMessage(), taskId));
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
        }
        catch (SQLException e) {
            LOG.error(format("Can't update task cause: %s. Employee: %s", e.getMessage(), task));
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
        }
        catch (SQLException e) {
            LOG.error(format("Can't delete task cause: %s. Employee: %s", e.getMessage(), taskId));
            throw new RepositoryException(e);
        }
    }

    private void setValuesToStatement(Task task, PreparedStatement statement) throws SQLException {
        statement.setString(1, task.getStatus().name());
        statement.setString(2, task.getName());
        statement.setLong(3, task.getProject().getId());

        if (task.getWorkTime() != null) {
            statement.setInt(4, task.getWorkTime());
        }
        else {
            statement.setNull(4, Types.INTEGER);
        }

        if (task.getStartDate() != null) {
            statement.setDate(5, Date.valueOf(task.getStartDate()));
        }
        else {
            statement.setNull(5, Types.DATE);
        }

        if (task.getEndDate() != null) {
            statement.setDate(6, Date.valueOf(task.getEndDate()));
        }
        else {
            statement.setNull(6, Types.DATE);
        }

        if (task.getEmployee() != null) {
            statement.setLong(7, task.getEmployee().getId());
        }
        else {
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
