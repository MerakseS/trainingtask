package com.qulix.losevsa.trainingtask.web.controller.command.projectcommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultTaskRepository;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.DefaultProjectService;
import com.qulix.losevsa.trainingtask.web.service.Service;

/**
 * Show project list command.
 */
public class ShowProjectListCommand implements Command {

    private final Service<Project, ProjectDto> projectService;

    private static final String PROJECT_LIST_PATH = "/WEB-INF/jsp/projectList.jsp";

    public ShowProjectListCommand(Service<Project, ProjectDto> projectService) {
        this.projectService = projectService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projectList = projectService.getAll();

        request.setAttribute("projectList", projectList);
        request.getRequestDispatcher(PROJECT_LIST_PATH).forward(request, response);
    }
}
