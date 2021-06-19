package repository;

import entity.Task;

import java.util.List;

public interface TaskRepository {

    Task saveTask(Task task);

    List<Task> getAllTasks();

    List<Task> getTaskListByProjectId(long projectId);

    Task getTaskById(long id);

    Task updateTask(Task task);

    long deleteTaskById(long id);
}
