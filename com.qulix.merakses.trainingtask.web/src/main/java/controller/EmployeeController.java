package controller;

import controller.command.Command;
import controller.command.CommandProvider;
import controller.command.employeeCommand.EmployeeCommandProvider;
import service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Employee", value = "/employee/*")
public class EmployeeController extends HttpServlet {
    private final CommandProvider commandProvider = new EmployeeCommandProvider();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String commandName = request.getPathInfo();
            Command command = commandProvider.getCommand(commandName);
            command.execute(request, response);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
