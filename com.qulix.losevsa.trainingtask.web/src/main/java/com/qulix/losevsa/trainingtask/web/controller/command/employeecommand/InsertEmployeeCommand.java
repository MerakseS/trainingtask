package com.qulix.losevsa.trainingtask.web.controller.command.employeecommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.DefaultEmployeeService;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;

/**
 * Insert employee command.
 */
public class InsertEmployeeCommand implements Command {

    public static final Logger LOG = Logger.getLogger(InsertEmployeeCommand.class);

    private static final String EMPLOYEE_LIST_PATH = "/employee";
    private static final String NEW_EMPLOYEE_FORM_PATH = "/employee/new";

    private static final String FIRST_NAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String POSITION_PARAMETER = "position";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Service<Employee, EmployeeDto> employeeService = new DefaultEmployeeService(employeeRepository);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(request.getParameter(FIRST_NAME_PARAMETER));
        employeeDto.setSurname(request.getParameter(SURNAME_PARAMETER));
        employeeDto.setPatronymic(request.getParameter(PATRONYMIC_PARAMETER));
        employeeDto.setPosition(request.getParameter(POSITION_PARAMETER));

        try {
            employeeService.create(employeeDto);
            response.sendRedirect(EMPLOYEE_LIST_PATH);
        }
        catch (FieldNotFilledException e) {
            handleException(e.getMessage(), "Введите обязательные поля.", request, response);
        }
        catch (EmployeeFieldLengthExceededException e) {
            handleException(e.getMessage(), "Длина полей работника должна быть не больше 30 символов.", request, response);
        }
    }

    private void handleException(String logMessage, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn(logMessage);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(NEW_EMPLOYEE_FORM_PATH).forward(request, response);
    }
}
