package controller.command.employeeCommand.impl;

import controller.command.Command;
import service.EmployeeService;
import service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEmployeeCommand implements Command{
    private static final String EMPLOYEE_LIST_PATH = "/employee";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        long id = Long.parseLong(request.getParameter("id"));
        employeeService.deleteEmployee(id);
        response.sendRedirect(EMPLOYEE_LIST_PATH);
    }
}
