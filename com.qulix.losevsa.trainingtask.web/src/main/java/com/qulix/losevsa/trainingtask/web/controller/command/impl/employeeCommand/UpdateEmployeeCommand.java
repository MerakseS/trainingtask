package com.qulix.losevsa.trainingtask.web.controller.command.impl.employeeCommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.service.EmployeeService;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Update employee command.
 */
public class UpdateEmployeeCommand implements Command {

    public static final Logger LOG = Logger.getLogger(UpdateEmployeeCommand.class);

    private static final String EMPLOYEE_LIST_PATH = "/employee";
    private static final String EDIT_EMPLOYEE_FORM_PATH = "/employee/edit";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "id";
    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String POSITION_PARAMETER = "position";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        String firsName = request.getParameter(FIRSTNAME_PARAMETER);
        String surname = request.getParameter(SURNAME_PARAMETER);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        String position = request.getParameter(POSITION_PARAMETER);

        try {
            employeeService.updateEmployee(id, firsName, surname, patronymic, position);
            response.sendRedirect(EMPLOYEE_LIST_PATH);
        }
        catch (NotFoundException e) {
            LOG.warn(e.getMessage());
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("?????????????????? ?? id %d ???? ????????????????????!", id));
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
        catch (FieldNotFilledException e) {
            handleException(e.getMessage(), "?????????????? ???????????????????????? ????????.", request, response);
        }
        catch (EmployeeFieldLengthExceededException e) {
            handleException(e.getMessage(), "?????????? ?????????? ?????????????????? ???????????? ???????? ???? ???????????? 30 ????????????????.", request, response);
        }
    }

    private void handleException(String logMessage, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn(logMessage);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(EDIT_EMPLOYEE_FORM_PATH).forward(request, response);
    }
}
