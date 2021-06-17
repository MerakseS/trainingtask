package repository;

import entity.Project;

import java.util.List;

public interface ProjectRepository {

    Project saveProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(long id);

    Project updateProject(Project project);

    long deleteProjectById(long id);
    
}
