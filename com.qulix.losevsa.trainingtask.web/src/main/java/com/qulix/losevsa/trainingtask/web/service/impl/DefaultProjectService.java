package com.qulix.losevsa.trainingtask.web.service.impl;

import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.ProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.repository.TaskRepository;
import com.qulix.losevsa.trainingtask.web.service.ServiceException;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;

/**
 * The default implementation of the {@link ProjectService}
 */
public class DefaultProjectService implements ProjectService {

    private static final Logger LOG = Logger.getLogger(DefaultProjectService.class);

    private static final int NAME_MAX_LENGTH = 30;
    private static final int DESCRIPTION_MAX_LENGTH = 200;

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    /**
     * Instantiates a new Default project com.qulix.losevsa.trainingtask.web.service.
     */
    public DefaultProjectService() {
        RepositoryProvider provider = RepositoryProvider.getInstance();
        projectRepository = provider.getProjectRepository();
        taskRepository = provider.getTaskRepository();
    }

    @Override
    public Project createProject(String name, String description) {
        validateValues(name, description);

        Project project = new Project();
        project.setName(name);
        if (description != null && !description.isBlank()) {
            project.setDescription(description);
        }

        project = projectRepository.saveProject(project);
        LOG.info("Successfully created project with id " + project.getId());

        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        LOG.info("Getting all projects.");
        return projectRepository.getAllProjects();
    }

    @Override
    public Project getProject(long id) {
        Project project = projectRepository.getProjectById(id);
        if (project == null) {
            LOG.error("Project with id " + id + " doesn't exist.");
            throw new ServiceException("Проект с id " + id + " не существует!");
        }

        List<Task> taskList = taskRepository.getTaskListByProjectId(id);
        project.setTaskList(taskList);
        LOG.info("Successfully get project with id " + id);

        return project;
    }

    @Override
    public Project updateProject(long id, String name, String description) {
        checkThatProjectExists(id);

        validateValues(name, description);

        Project project = new Project();
        project.setId(id);
        project.setName(name);
        if (description != null && !description.isBlank()) {
            project.setDescription(description);
        }

        project = projectRepository.updateProject(project);
        LOG.info("Successfully updated project with id " + project.getId());

        return project;
    }

    @Override
    public long deleteProject(long id) {
        checkThatProjectExists(id);

        id = projectRepository.deleteProjectById(id);
        LOG.info("Successfully deleted project with id " + id);

        return id;
    }

    public void checkThatProjectExists(long id) {
        Project project = projectRepository.getProjectById(id);
        if (project == null) {
            LOG.error("Project with id " + id + " doesn't exist.");
            throw new ServiceException("Проект с id " + id + " не существует!");
        }
    }

    private void validateValues(String name, String description) {
        if (name == null || name.isBlank()) {
            LOG.warn(format("Required field are empty. Name: %s", name));
            throw new ServiceException("Введите обязательные поля.");
        }

        if (name.length() > NAME_MAX_LENGTH) {
            LOG.warn(format("Length of name is more then %d. Name: %s", NAME_MAX_LENGTH, name));
            throw new ServiceException(format("Длина наименования не больше %d символов.", NAME_MAX_LENGTH));
        }

        if (description != null && description.length() > DESCRIPTION_MAX_LENGTH) {
            LOG.warn(format("Length of patronymic is more then %d. Patronymic: %s", DESCRIPTION_MAX_LENGTH, description));
            throw new ServiceException(format("Длина описания не больше %d символов.", DESCRIPTION_MAX_LENGTH));
        }
    }
}
