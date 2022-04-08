package com.qulix.losevsa.trainingtask.web.dto;

public class ProjectDto {

    /**
     * name of the project.
     */
    private String name;

    /**
     * Description of the project.
     */
    private String description;

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
}
