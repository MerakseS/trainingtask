package com.qulix.losevsa.trainingtask.web.controller.command.impl.employeeCommand;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;

/**
 * The Delete employee command.
 */
public class DeleteEmployeeCommand implements Command {

    private static final String EMPLOYEE_LIST_PATH = "/employee";

    private static final String ID_PARAMETER = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        employeeService.deleteEmployee(id);

        response.sendRedirect(EMPLOYEE_LIST_PATH);
    }
}
