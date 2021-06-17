package service;

import entity.Project;

import java.util.List;

public interface ProjectService {

    Project createProject(String name, String description);

    List<Project> getAllProjects();

    Project getProject(long id);

    Project updateProject(long id, String name, String description);

    long deleteProject(long id);
    
}
