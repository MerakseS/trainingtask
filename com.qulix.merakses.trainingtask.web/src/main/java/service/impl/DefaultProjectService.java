package service.impl;

import entity.Project;
import entity.Task;
import org.apache.log4j.Logger;
import repository.ProjectRepository;
import repository.RepositoryException;
import repository.RepositoryProvider;
import repository.TaskRepository;
import service.ProjectService;
import service.ServiceException;
import service.TaskService;

import java.util.List;

/**
 * The default implementation of the {@link ProjectService}
 */
public class DefaultProjectService implements ProjectService {
    private static final Logger log = Logger.getLogger(DefaultProjectService.class);
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
    public Project createProject(String name, String description) {
        try {
            validateValues(name, description);

            Project project = new Project();
            project.setName(name);
            if (description != null && !description.isBlank()) {
                project.setDescription(description);
            }

            project = projectRepository.saveProject(project);
            log.info("Successfully created project with id " + project.getId());

            return project;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Project> getAllProjects() {
        try {
            log.info("Getting all projects.");
            return projectRepository.getAllProjects();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Project getProject(long id) {
        try {
            log.info("Getting project with id " + id);
            Project project = projectRepository.getProjectById(id);
            if (project == null) {
                log.error("Project with id " + id + " doesn't exist.");
                throw new ServiceException("Проект с id " + id + " не существует!");
            }

            List<Task> taskList = taskRepository.getTaskListByProjectId(id);
            project.setTaskList(taskList);

            return project;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Project updateProject(long id, String name, String description) {
        try {
            checkThatProjectExists(id);

            validateValues(name, description);

            Project project = new Project();
            project.setId(id);
            project.setName(name);
            if (description != null && !description.isBlank()) {
                project.setDescription(description);
            }

            project = projectRepository.updateProject(project);
            log.info("Successfully updated project with id " + project.getId());

            return project;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long deleteProject(long id) {
        try {
            checkThatProjectExists(id);

            id = projectRepository.deleteProjectById(id);
            log.info("Successfully deleted project with id " + id);

            return id;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void checkThatProjectExists(long id) {
        Project project = projectRepository.getProjectById(id);
        if (project == null) {
            log.error("Project with id " + id + " doesn't exist.");
            throw new ServiceException("Проект с id " + id + " не сущесвует!");
        }
    }

    private void validateValues(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new ServiceException("Введите обязательные поля.");
        }

        if (name.length() > 30) {
            throw new ServiceException("Длина наименования не больше 30 символов.");
        }

        if (description != null && description.length() > 200) {
            throw new ServiceException("Длина описания не больше 200 символов.");
        }
    }
}
