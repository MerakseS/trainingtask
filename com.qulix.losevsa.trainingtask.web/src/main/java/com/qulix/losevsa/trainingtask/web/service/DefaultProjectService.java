package com.qulix.losevsa.trainingtask.web.service;

import java.util.ArrayList;
import java.util.List;
import static java.lang.String.format;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.exception.DescriptionLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * The default {@link Project} implementation of {@link Service}.
 */
public class DefaultProjectService implements Service<Project, ProjectDto> {

    private static final Logger LOG = Logger.getLogger(DefaultProjectService.class);

    private static final int NAME_MAX_LENGTH = 30;
    private static final int DESCRIPTION_MAX_LENGTH = 200;

    private final Repository<Project> projectRepository;
    private final Repository<Task> taskRepository;

    /**
     * Instantiates a new Default project service.
     * @param projectRepository the repository for {@link Project}
     * @param taskRepository the repository for {@link Task}
     */
    public DefaultProjectService(Repository<Project> projectRepository, Repository<Task> taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(ProjectDto projectDto) {
        validateValues(projectDto);

        Project project = new Project();
        project.setName(projectDto.getName());
        if (projectDto.getDescription() != null && !projectDto.getDescription().isBlank()) {
            project.setDescription(projectDto.getDescription());
        }

        project = projectRepository.save(project);
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
            throw new NotFoundException(format("Project with id %d doesn't exist.", id));
        }

        List<Task> taskList = new ArrayList<>();
        for (Task task : taskRepository.getAll()) {
            if (task.getProject().getId() == id) {
                taskList.add(task);
            }
        }

        project.setTaskList(taskList);
        LOG.info(format("Successfully get project with id %d", id));

        return project;
    }

    @Override
    public void update(long id, ProjectDto projectDto) {
        Project project = projectRepository.getById(id);
        if (project == null) {
            throw new NotFoundException(format("Project with id %d doesn't exist.", id));
        }

        validateValues(projectDto);

        Project newProject = new Project();
        newProject.setId(id);
        newProject.setName(projectDto.getName());
        if (projectDto.getDescription() != null && !projectDto.getDescription().isBlank()) {
            newProject.setDescription(projectDto.getDescription());
        }

        newProject = projectRepository.update(newProject);
        LOG.info(format("Successfully updated project with id %d", newProject.getId()));
    }

    @Override
    public void delete(long id) {
        Project project = projectRepository.getById(id);
        if (project == null) {
            throw new NotFoundException(format("Project with id %d doesn't exist.", id));
        }

        id = projectRepository.deleteById(id);
        LOG.info(format("Successfully deleted project with id %d", id));
    }

    private void validateValues(ProjectDto projectDto) {
        if (projectDto.getName() == null || projectDto.getName().isBlank()) {
            throw new FieldNotFilledException(format("Required field are empty. Name: %s", projectDto.getName()));
        }

        if (projectDto.getName().length() > NAME_MAX_LENGTH) {
            throw new NameLengthExceededException(
                format("Length of name is more then %d. Name: %s", NAME_MAX_LENGTH, projectDto.getName())
            );
        }

        if (projectDto.getDescription() != null && projectDto.getDescription().length() > DESCRIPTION_MAX_LENGTH) {
            throw new DescriptionLengthExceededException(
                format("Length of patronymic is more then %d. Patronymic: %s", DESCRIPTION_MAX_LENGTH, projectDto.getDescription())
            );
        }
    }
}
