package com.qulix.losevsa.trainingtask.web.controller.command.impl.employeeCommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;

/**
 * The command that show employee form for adding new employee.
 */
public class ShowNewEmployeeFormCommand implements Command {

    private static final String EMPLOYEE_EDIT_PATH = "/WEB-INF/jsp/employeeEdit.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(EMPLOYEE_EDIT_PATH).forward(request, response);
    }
}
