package com.qulix.losevsa.trainingtask.web.repository;

import com.qulix.losevsa.trainingtask.web.entity.Project;

import java.util.List;

/**
 * The interface of com.qulix.losevsa.trainingtask.web.repository layer for working with {@link Project}
 */
public interface ProjectRepository {

    /**
     * Save project to com.qulix.losevsa.trainingtask.web.database.
     *
     * @param project the project
     * @return the project
     */
    Project saveProject(Project project);

    /**
     * Gets all projects from com.qulix.losevsa.trainingtask.web.database.
     *
     * @return the all projects
     */
    List<Project> getAllProjects();

    /**
     * Gets project by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the project by id
     */
    Project getProjectById(long id);

    /**
     * Update project in com.qulix.losevsa.trainingtask.web.database.
     *
     * @param project the project
     * @return the project
     */
    Project updateProject(Project project);

    /**
     * Delete project by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the long
     */
    long deleteProjectById(long id);

}
