package service;

import entity.Task;

import java.util.List;

public interface TaskService {

    Task createTask(String name, String strProjectId, String workTime, String startDate,
                    String endDate, String status, String  strEmployeeId);

    List<Task> getAllTasks();

    Task getTask(long taskId);

    Task updateTask(long taskId, String name, String strProjectId, String workTime, String startDate,
                    String endDate, String status, String strEmployeeId);

    long deleteTask(long taskId);
}
