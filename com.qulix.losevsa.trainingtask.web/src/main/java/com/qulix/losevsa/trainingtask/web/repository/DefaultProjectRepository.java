package com.qulix.losevsa.trainingtask.web.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.String.format;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

import com.qulix.losevsa.trainingtask.web.database.DatabaseConnection;
import com.qulix.losevsa.trainingtask.web.entity.Project;

/**
 * The default {@link Project} implementation of {@link Repository}
 */
public class DefaultProjectRepository implements Repository<Project> {

    private static final String PROJECT_ID_COLUMN_NAME = "ID";
    private static final String PROJECT_NAME_COLUMN_NAME = "NAME";
    private static final String PROJECT_DESCRIPTION_COLUMN_NAME = "DESCRIPTION";

    private static final String SAVE_PROJECT_QUERY = "INSERT INTO PROJECT (NAME, DESCRIPTION) VALUES (?, ?)";
    private static final String GET_ALL_PROJECTS_QUERY = "SELECT * FROM PROJECT";
    private static final String GET_PROJECT_BY_ID_QUERY = "SELECT * FROM PROJECT WHERE ID = ?";
    private static final String UPDATE_PROJECT_QUERY = "UPDATE PROJECT SET NAME = ?, DESCRIPTION = ? WHERE ID = ?";
    private static final String DELETE_PROJECT_QUERY = "DELETE FROM PROJECT WHERE ID = ?";

    @Override
    public Project save(Project project) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_PROJECT_QUERY, RETURN_GENERATED_KEYS)) {

            setValuesToStatement(project, statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(format("Can't save project. No rows affected. Project: %s", project));
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                project.setId(generatedKeys.getLong(1));
            }
            else {
                throw new QueryExecutionException(format("Can't save project. No ID obtained. Project: %s", project));
            }

            return project;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(format("Can't save project cause: %s. Employee: %s", e.getMessage(), project), e);
        }
    }

    @Override
    public List<Project> getAll() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PROJECTS_QUERY)) {

            ResultSet result = statement.executeQuery();
            List<Project> projectList = new ArrayList<>();
            while (result.next()) {
                Project project = getProjectByResultSet(result);
                projectList.add(project);
            }

            return projectList;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(format("Can't get all projects cause: %s", e.getMessage()), e);
        }
    }

    @Override
    public Project getById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PROJECT_BY_ID_QUERY)) {

            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            return result.next() ? getProjectByResultSet(result) : null;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(format("Can't get project cause: %s. Employee: %d", e.getMessage(), id), e);
        }
    }

    @Override
    public Project update(Project project) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT_QUERY)) {

            setValuesToStatement(project, statement);
            statement.setLong(3, project.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(format("Can't update project. No rows affected. Project: %s", project));
            }

            return project;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(format("Can't update project cause: %s. Employee: %s", e.getMessage(), project), e);
        }
    }

    @Override
    public long deleteById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PROJECT_QUERY)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new QueryExecutionException(format("Can't delete project. No rows affected. Project's id: %s", id));
            }

            return id;
        }
        catch (SQLException e) {
            throw new QueryExecutionException(format("Can't delete project cause: %s. Employee: %s", e.getMessage(), id), e);
        }
    }

    private void setValuesToStatement(Project project, PreparedStatement statement) throws SQLException {
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
    }

    private Project getProjectByResultSet(ResultSet result) throws SQLException {
        Project project = new Project();
        project.setId(result.getLong(PROJECT_ID_COLUMN_NAME));
        project.setName(result.getString(PROJECT_NAME_COLUMN_NAME));
        project.setDescription(result.getString(PROJECT_DESCRIPTION_COLUMN_NAME));

        return project;
    }
}
