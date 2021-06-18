package service;

import entity.Task;
import entity.enums.StatusEnum;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task createTask(String name, long projectId, int workTime, LocalDate startDate,
                    LocalDate endDate, StatusEnum status, long employeeId);

    List<Task> getAllTasks();

    Task getTask(long taskId);

    Task updateTask(long taskId, String name, long projectId, int workTime, LocalDate startDate,
                    LocalDate endDate, StatusEnum status, long employeeId);

    long deleteTask(long taskId);
}
