package service;

import entity.Task;
import entity.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task createTask(String name, Long projectId, String workTime, String startDate,
                    String endDate, String status, Long employeeId);

    List<Task> getAllTasks();

    List<Task> getTasksByProject(long projectId);

    Task getTask(long taskId);

    Task updateTask(long taskId, String name, Long projectId, String workTime, String startDate,
                    String endDate, String status, Long employeeId);

    long deleteTask(long taskId);
}
