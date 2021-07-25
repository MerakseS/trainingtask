package com.qulix.losevsa.trainingtask.web.repository.impl;

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
import com.qulix.losevsa.trainingtask.web.repository.ProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryException;

/**
 * The default implementation of {@link ProjectRepository}
 */
public class DefaultProjectRepository implements ProjectRepository {

    private static final String PROJECT_ID_COLUMN_NAME = "P_ID";
    private static final String PROJECT_NAME_COLUMN_NAME = "P_NAME";
    private static final String PROJECT_DESCRIPTION_COLUMN_NAME = "P_DESCRIPTION";

    private static final String SAVE_PROJECT_QUERY = "INSERT INTO PROJECT (P_NAME, P_DESCRIPTION) VALUES (?, ?)";
    private static final String GET_ALL_PROJECTS_QUERY = "SELECT * FROM PROJECT";
    private static final String GET_PROJECT_BY_ID_QUERY = "SELECT * FROM PROJECT WHERE P_ID = ?";
    private static final String UPDATE_PROJECT_QUERY = "UPDATE PROJECT SET P_NAME = ?, P_DESCRIPTION = ? WHERE P_ID = ?";
    private static final String DELETE_PROJECT_QUERY = "DELETE FROM PROJECT WHERE P_ID = ?";

    @Override
    public Project saveProject(Project project) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_PROJECT_QUERY, RETURN_GENERATED_KEYS)) {

            setValuesToStatement(project, statement);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't save project. No rows affected. Project: %s", project));
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException(format("Can't save project. No ID obtained. Project: %s", project));
                }
            }

            return project;
        }
        catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PROJECTS_QUERY)) {

            try (ResultSet result = statement.executeQuery()) {
                List<Project> projectList = new ArrayList<>();
                while (result.next()) {
                    Project project = getProjectByResultSet(result);
                    projectList.add(project);
                }

                return projectList;
            }
        }
        catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Project getProjectById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PROJECT_BY_ID_QUERY)) {

            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? getProjectByResultSet(result) : null;
            }
        }
        catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Project updateProject(Project project) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT_QUERY)) {

            setValuesToStatement(project, statement);
            statement.setLong(3, project.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't update project. No rows affected. Project: %s", project));
            }

            return project;
        }
        catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public long deleteProjectById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PROJECT_QUERY)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't delete project. No rows affected. Project's id: %s", id));
            }

            return id;
        }
        catch (SQLException e) {
            throw new RepositoryException(e);
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