package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.exception.DescriptionLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * The interface of business logic for working with {@link Project}
 */
public interface ProjectService {

    /**
     * Creates project.
     *
     * @param name        the name of the project
     * @param description the description of the project
     * @throws FieldNotFilledException if required fields are empty
     * @throws NameLengthExceededException if name's length is bigger than 30.
     * @throws DescriptionLengthExceededException if description's length is bigger than 200.
     */
    void createProject(String name, String description);

    /**
     * Gets all projects.
     *
     * @return the {@link List} of all projects
     */
    List<Project> getAllProjects();

    /**
     * Gets project by project id.
     *
     * @param projectId the project id
     * @return the project
     * @throws NotFoundException if project with that id doesn't exist
     */
    Project getProject(long projectId);

    /**
     * Update project by id.
     *
     * @param projectId   the project id
     * @param name        the name
     * @param description the description
     * @throws NotFoundException if project with that id doesn't exist
     * @throws FieldNotFilledException if required fields are empty
     * @throws NameLengthExceededException if name's length is bigger than 30.
     * @throws DescriptionLengthExceededException if description's length is bigger than 200.
     */
    void updateProject(long projectId, String name, String description);

    /**
     * Delete project by id.
     *
     * @param projectId the project id
     * @throws NotFoundException if project with that id doesn't exist
     */
    void deleteProject(long projectId);

}
