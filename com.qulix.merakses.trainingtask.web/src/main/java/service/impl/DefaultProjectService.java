package service.impl;

import entity.Project;
import org.apache.log4j.Logger;
import repository.ProjectRepository;
import repository.RepositoryException;
import repository.RepositoryProvider;
import service.ProjectService;
import service.ServiceException;

import java.util.List;

public class DefaultProjectService implements ProjectService {
    private static final Logger log = Logger.getLogger(DefaultProjectService.class);
    private final ProjectRepository projectRepository;

    public DefaultProjectService() {
        RepositoryProvider provider = RepositoryProvider.getInstance();
        projectRepository = provider.getProjectRepository();
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
                throw new ServiceException("Проект с id " + id + " не сущесвует!");
            }

            return project;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Project updateProject(long id, String name, String description) {
        try {
            Project project = projectRepository.getProjectById(id);
            if (project == null) {
                log.error("Project with id " + id + " doesn't exist.");
                throw new ServiceException("Проект с id " + id + " не сущесвует!");
            }

            validateValues(name, description);

            Project newProject = new Project();
            newProject.setId(id);
            newProject.setName(name);
            if (description != null && !description.isBlank()) {
                newProject.setDescription(description);
            }

            project = projectRepository.updateProject(newProject);
            log.info("Successfully updated project with id " + project.getId());

            return project;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long deleteProject(long id) {
        try {
            Project project = projectRepository.getProjectById(id);
            if (project == null) {
                log.error("Project with id " + id + " doesn't exist.");
                throw new ServiceException("Проект с id " + id + " не сущесвует!");
            }

            id = projectRepository.deleteProjectById(id);
            log.info("Successfully deleted project with id " + id);

            return id;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
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
