package repository.impl;

import entity.Task;
import repository.TaskRepository;

import java.util.List;

public class DefaultTaskRepository implements TaskRepository {
    @Override
    public Task saveProject(Task task) {
        return null;
    }

    @Override
    public List<Task> getAllProjects() {
        return null;
    }

    @Override
    public Task getProjectById(long id) {
        return null;
    }

    @Override
    public Task updateProject(Task task) {
        return null;
    }

    @Override
    public long deleteProjectById(long id) {
        return 0;
    }
}
