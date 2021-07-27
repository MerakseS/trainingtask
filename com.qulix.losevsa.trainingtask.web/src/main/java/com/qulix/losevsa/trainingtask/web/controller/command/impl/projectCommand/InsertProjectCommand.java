package com.qulix.losevsa.trainingtask.web.controller.command.impl.projectCommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.service.IncorrectInputException;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;

/**
 * The Insert project command.
 */
public class InsertProjectCommand implements Command {

    private static final String PROJECT_LIST_PATH = "/project";
    private static final String NEW_PROJECT_FORM_PATH = "/project/new";

    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        String name = request.getParameter(NAME_PARAMETER);
        String description = request.getParameter(DESCRIPTION_PARAMETER);

        try {
            projectService.createProject(name, description);
            response.sendRedirect(PROJECT_LIST_PATH);
        }
        catch (IncorrectInputException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(NEW_PROJECT_FORM_PATH).forward(request, response);
        }
    }
}
