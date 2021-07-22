package com.qulix.losevsa.trainingtask.web.controller.command.impl.employeeCommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.ServiceException;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;

/**
 * The Insert employee command.
 */
public class InsertEmployeeCommand implements Command {

    private static final String EMPLOYEE_LIST_PATH = "/employee";
    private static final String NEW_EMPLOYEE_FORM_PATH = "/employee/new";

    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String POSITION_PARAMETER = "position";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        String firsName = request.getParameter(FIRSTNAME_PARAMETER);
        String surname = request.getParameter(SURNAME_PARAMETER);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        String position = request.getParameter(POSITION_PARAMETER);

        try {
            employeeService.createEmployee(firsName, surname, patronymic, position);
            response.sendRedirect(EMPLOYEE_LIST_PATH);
        }
        catch (ServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(NEW_EMPLOYEE_FORM_PATH).forward(request, response);
        }
    }
}
