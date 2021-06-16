package controller.command.employeeCommand.impl;

import controller.command.Command;
import service.EmployeeService;
import service.ServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InsertEmployeeCommand implements Command {
    private static final String EMPLOYEE_LIST_PATH = "/employee";

    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String POSITION_PARAMETER = "position";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        String firsName = request.getParameter(FIRSTNAME_PARAMETER);
        String surname = request.getParameter(SURNAME_PARAMETER);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        String position = request.getParameter(POSITION_PARAMETER);

        employeeService.createEmployee(firsName, surname, patronymic, position);

        response.sendRedirect(EMPLOYEE_LIST_PATH);
    }
}
