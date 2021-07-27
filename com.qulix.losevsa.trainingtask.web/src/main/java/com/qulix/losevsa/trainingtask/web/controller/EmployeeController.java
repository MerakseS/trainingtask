package com.qulix.losevsa.trainingtask.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.EmployeeCommandProvider;

/**
 * The Controller for employee.
 */
@WebServlet(name = "EmployeeController", value = "/employee/*")
public class EmployeeController extends HttpServlet {

    private final EmployeeCommandProvider employeeCommandProvider = new EmployeeCommandProvider();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandName = request.getPathInfo();
        Command command = employeeCommandProvider.getCommand(commandName);
        command.execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
