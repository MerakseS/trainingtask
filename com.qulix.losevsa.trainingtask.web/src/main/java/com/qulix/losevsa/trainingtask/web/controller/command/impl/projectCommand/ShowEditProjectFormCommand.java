package com.qulix.losevsa.trainingtask.web.controller.command.impl.projectCommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.ProjectService;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Show edit project form command.
 */
public class ShowEditProjectFormCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowEditProjectFormCommand.class);

    private static final String PROJECT_EDIT_PATH = "/WEB-INF/jsp/projectEdit.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "id";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));

        try {
            Project project = projectService.getProject(id);
            request.setAttribute("project", project);
            request.getRequestDispatcher(PROJECT_EDIT_PATH).forward(request, response);
        }
        catch (NotFoundException e) {
            LOG.warn(e.getMessage());
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("Сотрудник с id %d не существует!", id));
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
