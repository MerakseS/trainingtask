package com.qulix.losevsa.trainingtask.web.repository;

import java.util.List;

import com.qulix.losevsa.trainingtask.web.entity.Project;

/**
 * The interface of repository layer for working with {@link Project}.
 */
public interface ProjectRepository {

    /**
     * Save project to database.
     *
     * @param project the project
     * @return the project
     * @throws QueryExecutionException if a database access error occurs
     */
    Project saveProject(Project project);

    /**
     * Gets all projects from database.
     *
     * @return the all projects
     * @throws QueryExecutionException if a database access error occurs
     */
    List<Project> getAllProjects();

    /**
     * Gets project by id from database.
     *
     * @param id the id
     * @return the project by id
     * @throws QueryExecutionException if a database access error occurs
     */
    Project getProjectById(long id);

    /**
     * Update project in database.
     *
     * @param project the project
     * @return the project
     * @throws QueryExecutionException if a database access error occurs
     */
    Project updateProject(Project project);

    /**
     * Delete project by id from database.
     *
     * @param id the id
     * @return the long
     * @throws QueryExecutionException if a database access error occurs
     */
    long deleteProjectById(long id);

}
