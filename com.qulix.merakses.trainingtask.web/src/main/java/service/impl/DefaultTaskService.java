package service.impl;

import entity.Task;
import entity.enums.StatusEnum;
import service.TaskService;

import java.time.LocalDate;
import java.util.List;

public class DefaultTaskService implements TaskService {
    @Override
    public Task createTask(String name, long projectId, int workTime, LocalDate startDate, LocalDate endDate, StatusEnum status, long employeeId) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public Task getTask(long taskId) {
        return null;
    }

    @Override
    public Task updateTask(long taskId, String name, long projectId, int workTime, LocalDate startDate, LocalDate endDate, StatusEnum status, long employeeId) {
        return null;
    }

    @Override
    public long deleteTask(long taskId) {
        return 0;
    }
}
