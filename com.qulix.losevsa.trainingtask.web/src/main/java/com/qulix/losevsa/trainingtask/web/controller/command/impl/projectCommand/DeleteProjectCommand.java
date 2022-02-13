package com.qulix.losevsa.trainingtask.web.controller.command.impl.projectCommand;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;

/**
 * The Delete project command.
 */
public class DeleteProjectCommand implements Command {

    private static final String PROJECT_LIST_PATH = "/project";

    private static final String ID_PARAMETER = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        projectService.deleteProject(id);

        response.sendRedirect(PROJECT_LIST_PATH);
    }
}
