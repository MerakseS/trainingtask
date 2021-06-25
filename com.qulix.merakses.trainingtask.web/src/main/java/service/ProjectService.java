package service;

import entity.Project;

import java.util.List;

public interface ProjectService {

    Project createProject(String name, String description);

    List<Project> getAllProjects();

    Project getProject(long projectId);

    Project updateProject(long projectId, String name, String description);

    long deleteProject(long projectId);

    void checkThatProjectExists(long projectId);

}
