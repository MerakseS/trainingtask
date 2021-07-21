package service;

import entity.Project;

import java.util.List;

/**
 * The interface of business logic for working with {@link Project}
 */
public interface ProjectService {

    /**
     * Creates project.
     *
     * @param name        the name of the project
     * @param description the description of the project
     * @return the project
     * @throws ServiceException if data is incorrect
     */
    Project createProject(String name, String description);

    /**
     * Gets all projects.
     *
     * @return the {@link List} of all projects
     * @throws ServiceException if there is no connection to database
     */
    List<Project> getAllProjects();

    /**
     * Gets project by project id.
     *
     * @param projectId the project id
     * @return the project
     * @throws ServiceException if project with that id doesn't exists
     */
    Project getProject(long projectId);

    /**
     * Update project by id.
     *
     * @param projectId   the project id
     * @param name        the name
     * @param description the description
     * @return the project
     * @throws ServiceException if data is incorrect
     */
    Project updateProject(long projectId, String name, String description);

    /**
     * Delete project by id.
     *
     * @param projectId the project id
     * @return the id of the deleted project
     * @throws ServiceException if project with that id doesn't exists
     */
    long deleteProject(long projectId);

    /**
     * Check that project exists.
     *
     * @param projectId the project id
     * @throws ServiceException if project doesn't exists
     */
    void checkThatProjectExists(long projectId);

}
