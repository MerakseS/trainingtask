package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Project;

/**
 * The interface of business logic for working with {@link Project}
 */
public interface ProjectService {

    /**
     * Creates project.
     *
     * @param name        the name of the project
     * @param description the description of the project
     * @throws IncorrectInputException if data is incorrect
     */
    void createProject(String name, String description);

    /**
     * Gets all projects.
     *
     * @return the {@link List} of all projects
     * @throws IncorrectInputException if there is no connection to database
     */
    List<Project> getAllProjects();

    /**
     * Gets project by project id.
     *
     * @param projectId the project id
     * @return the project
     * @throws IncorrectInputException if project with that id doesn't exist
     */
    Project getProject(long projectId);

    /**
     * Update project by id.
     *
     * @param projectId   the project id
     * @param name        the name
     * @param description the description
     * @throws IncorrectInputException if data is incorrect
     */
    void updateProject(long projectId, String name, String description);

    /**
     * Delete project by id.
     *
     * @param projectId the project id
     * @throws IncorrectInputException if project with that id doesn't exist
     */
    void deleteProject(long projectId);

}
