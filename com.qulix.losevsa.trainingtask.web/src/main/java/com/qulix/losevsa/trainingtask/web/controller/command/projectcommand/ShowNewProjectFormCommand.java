package com.qulix.losevsa.trainingtask.web.controller.command.projectcommand;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Project;

/**
 * Show new project form command.
 */
public class ShowNewProjectFormCommand implements Command {

    private static final String EDITED_PROJECT_ATTRIBUTE_NAME = "editedProject";

    private static final String PROJECT_EDIT_PATH = "/WEB-INF/jsp/projectEdit.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(EDITED_PROJECT_ATTRIBUTE_NAME) == null) {
            Project project = new Project();
            project.setTaskList(new ArrayList<>());
            session.setAttribute(EDITED_PROJECT_ATTRIBUTE_NAME, project);
        }

        request.getRequestDispatcher(PROJECT_EDIT_PATH).forward(request, response);
    }
}
