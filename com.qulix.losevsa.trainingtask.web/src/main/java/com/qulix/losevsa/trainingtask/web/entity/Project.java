package com.qulix.losevsa.trainingtask.web.entity;

import java.util.List;

/**
 * Represents a project.
 */
public class Project {
    /**
     * Identifier of the project
     */
    private long id;
    /**
     * name of the project
     */
    private String name;
    /**
     * Description of the project
     */
    private String description;
    /**
     * List of tasks of the project
     *
     * @see Task
     */
    private List<Task> taskList;

    /**
     * Gets {@link Project#id}.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets {@link Project#id}.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets {@link Project#name}.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets {@link Project#name}.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets {@link Project#description}.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets {@link Project#description}.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets {@link Project#taskList}.
     *
     * @return the task list
     */
    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Sets {@link Project#taskList}.
     *
     * @param taskList the task list
     */
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
