package repository;

import entity.Task;

import java.util.List;

public interface TaskRepository {

    Task saveProject(Task task);

    List<Task> getAllProjects();

    Task getProjectById(long id);

    Task updateProject(Task task);

    long deleteProjectById(long id);
}
