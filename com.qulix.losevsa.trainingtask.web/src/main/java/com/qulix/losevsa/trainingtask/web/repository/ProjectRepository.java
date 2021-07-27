package com.qulix.losevsa.trainingtask.web.repository;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Project;

/**
 * The interface of com.qulix.losevsa.trainingtask.web.repository layer for working with {@link Project}
 */
public interface ProjectRepository {

    /**
     * Save project to com.qulix.losevsa.trainingtask.web.database.
     *
     * @param project the project
     * @return the project
     * @throws QueryExecutionException if a database access error occurs
     */
    Project saveProject(Project project);

    /**
     * Gets all projects from com.qulix.losevsa.trainingtask.web.database.
     *
     * @return the all projects
     * @throws QueryExecutionException if a database access error occurs
     */
    List<Project> getAllProjects();

    /**
     * Gets project by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the project by id
     * @throws QueryExecutionException if a database access error occurs
     */
    Project getProjectById(long id);

    /**
     * Update project in com.qulix.losevsa.trainingtask.web.database.
     *
     * @param project the project
     * @return the project
     * @throws QueryExecutionException if a database access error occurs
     */
    Project updateProject(Project project);

    /**
     * Delete project by id from com.qulix.losevsa.trainingtask.web.database.
     *
     * @param id the id
     * @return the long
     * @throws QueryExecutionException if a database access error occurs
     */
    long deleteProjectById(long id);

}
