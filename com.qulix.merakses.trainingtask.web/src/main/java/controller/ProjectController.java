package controller;

import controller.command.Command;
import controller.command.CommandProvider;
import controller.command.impl.ProjectCommandProvider;
import service.ServiceException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProjectController", value = "/project/*")
public class ProjectController extends HttpServlet {
    private final CommandProvider projectCommandProvider = new ProjectCommandProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String commandName = request.getPathInfo();
            Command command = projectCommandProvider.getCommand(commandName);
            command.execute(request, response);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
