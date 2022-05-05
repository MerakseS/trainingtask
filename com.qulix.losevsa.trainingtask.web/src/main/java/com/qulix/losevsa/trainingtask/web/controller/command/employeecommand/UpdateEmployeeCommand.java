package com.qulix.losevsa.trainingtask.web.controller.command.employeecommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Update employee command.
 */
public class UpdateEmployeeCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateEmployeeCommand.class);

    private static final String EMPLOYEE_LIST_PATH = "/employee";
    private static final String EDIT_EMPLOYEE_FORM_PATH = "/employee/edit";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "id";
    private static final String FIRST_NAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String POSITION_PARAMETER = "position";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final Service<Employee> employeeService;

    /**
     * Instantiates a new Update employee command.
     *
     * @param employeeService the employee service
     */
    public UpdateEmployeeCommand(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Employee employee = new Employee();
            employee.setId(Long.parseLong(request.getParameter(ID_PARAMETER)));
            employee.setFirstName(request.getParameter(FIRST_NAME_PARAMETER));
            employee.setSurname(request.getParameter(SURNAME_PARAMETER));
            employee.setPosition(request.getParameter(POSITION_PARAMETER));

            String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
            if (patronymic != null && !patronymic.isBlank()) {
                employee.setPatronymic(patronymic);
            }

            employeeService.update(employee);
            response.sendRedirect(EMPLOYEE_LIST_PATH);
        }
        catch (NotFoundException | NumberFormatException e) {
            LOG.warn("Can't update employee cause:", e);
            request.setAttribute(
                ERROR_ATTRIBUTE_NAME,
                format("Сотрудник с id %s не существует!", request.getParameter(ID_PARAMETER))
            );
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
        catch (FieldNotFilledException e) {
            handleException(e, "Введите обязательные поля.", request, response);
        }
        catch (EmployeeFieldLengthExceededException e) {
            handleException(e, "Длина полей работника должна быть не больше 30 символов.", request, response);
        }
    }

    private void handleException(Exception e, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn("Can't update employee cause:", e);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(EDIT_EMPLOYEE_FORM_PATH).forward(request, response);
    }
}
