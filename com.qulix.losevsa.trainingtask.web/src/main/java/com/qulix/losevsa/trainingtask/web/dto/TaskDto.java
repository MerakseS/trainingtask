package com.qulix.losevsa.trainingtask.web.dto;

import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.entity.TaskStatus;

/**
 * The Data transfer object of the {@link Task}.
 */
public class TaskDto {

    /**
     * Status of the task.
     *
     * @see TaskStatus
     */
    private String taskStatus;

    /**
     * Name of the task.
     */
    private String name;

    /**
     * Project that task belongs.
     */
    private String projectId;

    /**
     * The amount of time it takes to complete the task, hours.
     */
    private String workTime;

    /**
     * Start date of the task.
     */
    private String startDate;

    /**
     * End date of the task.
     */
    private String endDate;

    /**
     * Executor of the task.
     */
    private String employeeId;

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
