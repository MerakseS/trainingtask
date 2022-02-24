package com.qulix.losevsa.trainingtask.web.controller.command.impl.projectCommand;

import java.io.IOException;

import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Delete project command.
 */
public class DeleteProjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteProjectCommand.class);

    private static final String PROJECT_LIST_PATH = "/project";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "id";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));

        try {
            projectService.deleteProject(id);
            response.sendRedirect(PROJECT_LIST_PATH);
        }
        catch (NotFoundException e) {
            LOG.warn(e.getMessage());
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("Проект с id %d не существует!", id));
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
