package com.qulix.losevsa.trainingtask.web.service;

import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.exception.EndDateEarlierStartDateException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeNegativeException;

/**
 * The default {@link Task} implementation of {@link Service}.
 */
public class DefaultTaskService implements Service<Task> {

    private static final Logger LOG = Logger.getLogger(DefaultTaskService.class);

    private static final int NAME_MAX_LENGTH = 50;

    private final Repository<Task> taskRepository;

    /**
     * Instantiates a new Default task service.
     *
     * @param taskRepository the repository for {@link Task}
     */
    public DefaultTaskService(Repository<Task> taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(Task task) {
        validateValues(task);
        task = taskRepository.save(task);
        LOG.info(format("Successfully created task with id %d", task.getId()));
    }

    @Override
    public List<Task> getAll() {
        LOG.info("Getting all tasks");
        return taskRepository.getAll();
    }

    @Override
    public Task get(long taskId) {
        LOG.info(format("Getting task with id %d", taskId));
        Task task = taskRepository.getById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        return task;
    }

    @Override
    public void update(Task task) {
        Task oldTask = taskRepository.getById(task.getId());
        if (oldTask == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", task.getId()));
        }

        validateValues(task);
        task = taskRepository.update(task);
        LOG.info(format("Successfully updated task with id %d", task.getId()));
    }

    @Override
    public void delete(long taskId) {
        Task task = taskRepository.getById(taskId);
        if (task == null) {
            throw new NotFoundException(format("Task with id %d doesn't exist.", taskId));
        }

        taskId = taskRepository.deleteById(taskId);
        LOG.info(format("Successfully deleted task with id %d", taskId));
    }

    private void validateValues(Task task) {
        if (task.getName() == null || task.getName().isBlank() || task.getTaskStatus() == null) {
            throw new FieldNotFilledException(
                format("Required fields are empty. Name: %s, task status: %s", task.getName(), task.getTaskStatus())
            );
        }

        if (task.getName().length() > NAME_MAX_LENGTH) {
            throw new NameLengthExceededException(
                format("Length of name is more then %d. Name: %s", NAME_MAX_LENGTH, task.getName())
            );
        }

        if (task.getWorkTime() != null && task.getWorkTime() < 0) {
            throw new WorkTimeNegativeException(format("Work time is below zero. Work time: %s", task.getWorkTime()));
        }

        if (task.getStartDate() != null && task.getEndDate() != null && task.getEndDate().isBefore(task.getStartDate())) {
            throw new EndDateEarlierStartDateException(
                format("Еnd date is earlier than start date. Start date: %s, End date: %s", task.getStartDate(), task.getEndDate())
            );
        }
    }
}
