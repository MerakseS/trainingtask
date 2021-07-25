package com.qulix.losevsa.trainingtask.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.controller.command.CommandProvider;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.ProjectCommandProvider;
import com.qulix.losevsa.trainingtask.web.service.ServiceException;

/**
 * The Controller for project.
 */
@WebServlet(name = "ProjectController", value = "/project/*")
public class ProjectController extends HttpServlet {

    private final CommandProvider projectCommandProvider = new ProjectCommandProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String commandName = request.getPathInfo();
            Command command = projectCommandProvider.getCommand(commandName);
            command.execute(request, response);
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}