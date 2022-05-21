package com.qulix.losevsa.trainingtask.web.controller.command.projectcommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.Service;

/**
 * Show project list command.
 */
public class ShowProjectListCommand implements Command {

    private static final String EDITED_PROJECT_ATTRIBUTE_NAME = "editedProject";

    private static final String PROJECT_LIST_PATH = "/WEB-INF/jsp/projectList.jsp";

    private final Service<Project> projectService;

    /**
     * Instantiates a new Show project list command.
     *
     * @param projectService the project service
     */
    public ShowProjectListCommand(Service<Project> projectService) {
        this.projectService = projectService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projectList = projectService.getAll();
        request.setAttribute("projectList", projectList);

        HttpSession session = request.getSession();
        if (session.getAttribute(EDITED_PROJECT_ATTRIBUTE_NAME) != null) {
            session.removeAttribute(EDITED_PROJECT_ATTRIBUTE_NAME);
        }

        request.getRequestDispatcher(PROJECT_LIST_PATH).forward(request, response);
    }
}
