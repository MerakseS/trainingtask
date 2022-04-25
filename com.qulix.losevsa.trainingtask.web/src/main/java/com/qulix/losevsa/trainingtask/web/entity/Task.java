package com.qulix.losevsa.trainingtask.web.entity;

import java.time.LocalDate;
import static java.lang.String.format;

/**
 * Represents a task.
 */
public class Task {

    /**
     * Identifier of the task.
     */
    private long id;

    /**
     * Status of the task.
     *
     * @see TaskStatus
     */
    private TaskStatus taskStatus;

    /**
     * Name of the task.
     */
    private String name;

    /**
     * Project that task belongs.
     */
    private Project project;

    /**
     * The amount of time it takes to complete the task, hours.
     */
    private Integer workTime;

    /**
     * Start date of the task.
     */
    private LocalDate startDate;

    /**
     * End date of the task.
     */
    private LocalDate endDate;

    /**
     * Executor of the task.
     */
    private Employee employee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return format("%s [%s], due date %s--%s (%d), project: %d, employee: %d",
            name,
            taskStatus != null ? taskStatus.getName() : null,
            startDate,
            endDate,
            workTime,
            project != null ? project.getId() : null,
            employee != null ? employee.getId() : null
        );
    }
}
