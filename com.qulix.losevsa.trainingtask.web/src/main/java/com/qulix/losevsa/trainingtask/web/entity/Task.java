package com.qulix.losevsa.trainingtask.web.entity;

import com.qulix.losevsa.trainingtask.web.entity.enums.Status;

import java.time.LocalDate;

/**
 * Represents a task
 */
public class Task {
    /**
     * Identifier of the task.
     */
    private long id;

    /**
     * Status of the task
     *
     * @see Status
     */
    private Status status;

    /**
     * Name of the task
     */
    private String name;

    /**
     * Project that task belong
     */
    private Project project;

    /**
     * The amount of time it takes to complete the task, hours
     */
    private Integer workTime;

    /**
     * Start date of the task
     */
    private LocalDate startDate;

    /**
     * End date of the task
     */
    private LocalDate endDate;

    /**
     * Executor of the task
     */
    private Employee employee;

    /**
     * Gets {@link Task#id}.
     *
     * @return the task id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets {@link Task#id}.
     *
     * @param id the task id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets {@link Task#status}.
     *
     * @return the task status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets {@link Task#status}.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets {@link Task#name}.
     *
     * @return the task name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets {@link Task#name}.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets {@link Task#project}.
     *
     * @return the task project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets {@link Task#project}.
     *
     * @param project the project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets {@link Task#workTime}.
     *
     * @return the work time
     */
    public Integer getWorkTime() {
        return workTime;
    }

    /**
     * Sets {@link Task#workTime}.
     *
     * @param workTime the work time
     */
    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    /**
     * Gets {@link Task#startDate}.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets {@link Task#startDate}.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets {@link Task#endDate}.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets {@link Task#endDate}.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets {@link Task#employee}.
     *
     * @return the task executor
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets {@link Task#employee}.
     *
     * @param employee the executor
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
