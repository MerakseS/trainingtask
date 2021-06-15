package controller.command.employeeCommand.impl;

import controller.command.Command;
import service.EmployeeService;
import service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateEmployeeCommand implements Command {
    private static final String EMPLOYEE_LIST_PATH = "/employee";

    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        long id = Long.parseLong(request.getParameter("id"));
        String firsName = request.getParameter(FIRSTNAME_PARAMETER);
        String surname = request.getParameter(SURNAME_PARAMETER);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);

        employeeService.updateEmployee(id, firsName, surname, patronymic);
        response.sendRedirect(EMPLOYEE_LIST_PATH);
    }
}
