package com.qulix.losevsa.trainingtask.web.service.impl;

import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.ProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.RepositoryProvider;
import com.qulix.losevsa.trainingtask.web.repository.TaskRepository;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;
import com.qulix.losevsa.trainingtask.web.service.exception.DescriptionLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;

/**
 * The default implementation of the {@link ProjectService}.
 */
public class DefaultProjectService implements ProjectService {

    private static final Logger LOG = Logger.getLogger(DefaultProjectService.class);

    private static final int NAME_MAX_LENGTH = 30;
    private static final int DESCRIPTION_MAX_LENGTH = 200;

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    /**
     * Instantiates a new Default project service.
     */
    public DefaultProjectService() {
        RepositoryProvider provider = RepositoryProvider.getInstance();
        projectRepository = provider.getProjectRepository();
        taskRepository = provider.getTaskRepository();
    }

    @Override
    public void createProject(String name, String description) {
        validateValues(name, description);

        Project project = new Project();
        project.setName(name);
        if (description != null && !description.isBlank()) {
            project.setDescription(description);
        }

        project = projectRepository.saveProject(project);
        LOG.info(format("Successfully created project with id %d", project.getId()));
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
            throw new NotFoundException(format("Project with id %d doesn't exist.", id));
        }

        List<Task> taskList = taskRepository.getTaskListByProjectId(id);
        project.setTaskList(taskList);
        LOG.info(format("Successfully get project with id %d", id));

        return project;
    }

    @Override
    public void updateProject(long id, String name, String description) {
        Project project = projectRepository.getProjectById(id);
        if (project == null) {
            throw new NotFoundException(format("Project with id %d doesn't exist.", id));
        }

        validateValues(name, description);

        Project newProject = new Project();
        newProject.setId(id);
        newProject.setName(name);
        if (description != null && !description.isBlank()) {
            newProject.setDescription(description);
        }

        newProject = projectRepository.updateProject(newProject);
        LOG.info(format("Successfully updated project with id %d", newProject.getId()));
    }

    @Override
    public void deleteProject(long id) {
        Project project = projectRepository.getProjectById(id);
        if (project == null) {
            throw new NotFoundException(format("Project with id %d doesn't exist.", id));
        }

        id = projectRepository.deleteProjectById(id);
        LOG.info(format("Successfully deleted project with id %d", id));
    }

    private void validateValues(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new FieldNotFilledException(format("Required field are empty. Name: %s", name));
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new NameLengthExceededException(format("Length of name is more then %d. Name: %s", NAME_MAX_LENGTH, name));
        }

        if (description != null && description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new DescriptionLengthExceededException(
                format("Length of patronymic is more then %d. Patronymic: %s", DESCRIPTION_MAX_LENGTH, description)
            );
        }
    }
}
