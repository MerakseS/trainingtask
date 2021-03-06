package com.qulix.losevsa.trainingtask.web.controller.command.impl.projectCommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;

/**
 * Show new project form command.
 */
public class ShowNewProjectFormCommand implements Command {

    private static final String PROJECT_EDIT_PATH = "/WEB-INF/jsp/projectEdit.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(PROJECT_EDIT_PATH).forward(request, response);
    }
}
