package com.qulix.losevsa.trainingtask.web.service;

import java.util.ArrayList;
import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.exception.DescriptionLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.PageNotFoundException;

/**
 * The default {@link Project} implementation of {@link Service}.
 */
public class DefaultProjectService implements Service<Project> {

    private static final Logger LOG = Logger.getLogger(DefaultProjectService.class);

    private static final int NAME_MAX_LENGTH = 30;
    private static final int DESCRIPTION_MAX_LENGTH = 200;

    private final Repository<Project> projectRepository;
    private final Service<Task> taskService;

    /**
     * Instantiates a new Default project service.
     * @param projectRepository the repository for {@link Project}
     * @param taskService the service for {@link Task}
     */
    public DefaultProjectService(Repository<Project> projectRepository, Service<Task> taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
    }

    @Override
    public void create(Project project) {
        validate(project);
        project = projectRepository.save(project);

        for (Task task : project.getTaskList()) {
            task.setProject(project);
            taskService.create(task);
        }

        LOG.info(format("Successfully created project with id %d", project.getId()));
    }

    @Override
    public List<Project> getAll() {
        LOG.info("Getting all projects.");
        return projectRepository.getAll();
    }

    @Override
    public Project get(long id) {
        Project project = projectRepository.getById(id);
        if (project == null) {
            throw new PageNotFoundException(format("Project with id %d doesn't exist.", id));
        }

        project.setTaskList(getTaskListByProjectId(id));
        LOG.info(format("Successfully get project with id %d", id));
        return project;
    }

    @Override
    public void update(Project project) {
        Project oldProject = projectRepository.getById(project.getId());
        if (oldProject == null) {
            throw new PageNotFoundException(format("Project with id %d doesn't exist.", project.getId()));
        }

        validate(project);
        project = projectRepository.update(project);
        updateProjectTaskList(project);

        LOG.info(format("Successfully updated project with id %d", project.getId()));
    }

    @Override
    public void delete(long id) {
        Project project = projectRepository.getById(id);
        if (project == null) {
            throw new PageNotFoundException(format("Project with id %d doesn't exist.", id));
        }

        id = projectRepository.deleteById(id);
        LOG.info(format("Successfully deleted project with id %d", id));
    }

    @Override
    public void validate(Project project) {
        if (project.getName() == null || project.getName().isBlank()) {
            throw new FieldNotFilledException(format("Required field are empty. Name: %s", project.getName()));
        }

        if (project.getName().length() > NAME_MAX_LENGTH) {
            throw new NameLengthExceededException(
                format("Length of name is more then %d. Name: %s", NAME_MAX_LENGTH, project.getName())
            );
        }

        if (project.getDescription() != null && project.getDescription().length() > DESCRIPTION_MAX_LENGTH) {
            throw new DescriptionLengthExceededException(
                format("Length of patronymic is more then %d. Patronymic: %s", DESCRIPTION_MAX_LENGTH, project.getDescription())
            );
        }
    }

    private List<Task> getTaskListByProjectId(long id) {
        List<Task> taskList = new ArrayList<>();
        for (Task task : taskService.getAll()) {
            if (task.getProject().getId() == id) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    private void updateProjectTaskList(Project project) {
        for (Task newTask : project.getTaskList()) {
            newTask.setProject(project);
            if (newTask.getId() == 0) {
                taskService.create(newTask);
            }
            else {
                taskService.update(newTask);
            }
        }

        for (Task oldTask : getTaskListByProjectId(project.getId())) {
            boolean isDeleted = true;
            for (Task newTask : project.getTaskList()) {
                if (oldTask.getId() == newTask.getId()) {
                    isDeleted = false;
                    break;
                }
            }

            if (isDeleted) {
                taskService.delete(oldTask.getId());
            }
        }
    }
}
