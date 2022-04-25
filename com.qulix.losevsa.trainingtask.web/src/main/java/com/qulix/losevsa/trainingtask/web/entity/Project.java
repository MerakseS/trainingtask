package com.qulix.losevsa.trainingtask.web.entity;

import java.util.List;
import static java.lang.String.format;

/**
 * Represents a project.
 */
public class Project {

    /**
     * Identifier of the project.
     */
    private long id;

    /**
     * name of the project.
     */
    private String name;

    /**
     * Description of the project.
     */
    private String description;

    /**
     * List of tasks of the project.
     *
     * @see Task
     */
    private List<Task> taskList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return format("%s (%s)", name, description);
    }
}
