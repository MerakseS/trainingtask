package com.qulix.losevsa.trainingtask.web.controller;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.controller.command.CommandProvider;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.EmployeeCommandProvider;
import com.qulix.losevsa.trainingtask.web.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Controller for employee.
 */
@WebServlet(name = "EmployeeController", value = "/employee/*")
public class EmployeeController extends HttpServlet {
    private final CommandProvider employeeCommandProvider = new EmployeeCommandProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String commandName = request.getPathInfo();
            Command command = employeeCommandProvider.getCommand(commandName);
            command.execute(request, response);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
