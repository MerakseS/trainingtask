package repository;

import entity.Project;

import java.util.List;

/**
 * The interface of repository layer for working with {@link Project}
 */
public interface ProjectRepository {

    /**
     * Save project to database.
     *
     * @param project the project
     * @return the project
     */
    Project saveProject(Project project);

    /**
     * Gets all projects from database.
     *
     * @return the all projects
     */
    List<Project> getAllProjects();

    /**
     * Gets project by id from database.
     *
     * @param id the id
     * @return the project by id
     */
    Project getProjectById(long id);

    /**
     * Update project in database.
     *
     * @param project the project
     * @return the project
     */
    Project updateProject(Project project);

    /**
     * Delete project by id from database.
     *
     * @param id the id
     * @return the long
     */
    long deleteProjectById(long id);

}
