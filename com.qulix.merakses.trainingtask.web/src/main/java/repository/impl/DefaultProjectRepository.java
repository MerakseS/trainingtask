package repository.impl;

import database.DatabaseConnection;
import entity.Project;
import repository.ProjectRepository;
import repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

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

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't save project. No rows affected. Project: %s", project));
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException(format("Can't save project. No ID obtained. Project: %s", project));
                }
            }

            return project;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PROJECTS_QUERY)) {
            List<Project> projectList = new ArrayList<>();

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Project project = new Project();
                    project.setId(result.getLong(PROJECT_ID_COLUMN_NAME));
                    project.setName(result.getString(PROJECT_NAME_COLUMN_NAME));
                    project.setDescription(result.getString(PROJECT_DESCRIPTION_COLUMN_NAME));
                    projectList.add(project);
                }

                return projectList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Project getProjectById(long id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PROJECT_BY_ID_QUERY)) {

            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()){
                if (result.next()) {
                    Project project = new Project();
                    project.setId(result.getLong(PROJECT_ID_COLUMN_NAME));
                    project.setName(result.getString(PROJECT_NAME_COLUMN_NAME));
                    project.setDescription(result.getString(PROJECT_DESCRIPTION_COLUMN_NAME));
                    //TODO taskList

                    return project;
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Project updateProject(Project project) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT_QUERY)) {

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setLong(3, project.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException(format("Can't update project. No rows affected. Project: %s", project));
            }

            return project;
        } catch (SQLException e) {
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
                throw new SQLException(format("Can't update project. No rows affected. Project's id: %s", id));
            }

            return id;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
