package com.qulix.losevsa.trainingtask.web.controller.command.impl.projectCommand;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The Show project list command.
 */
public class ShowProjectListCommand implements Command {
    private static final String PROJECT_LIST_PATH = "/WEB-INF/jsp/projectList.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        List<Project> projectList = projectService.getAllProjects();

        request.setAttribute("projectList", projectList);
        request.getRequestDispatcher(PROJECT_LIST_PATH).forward(request, response);
    }
}
