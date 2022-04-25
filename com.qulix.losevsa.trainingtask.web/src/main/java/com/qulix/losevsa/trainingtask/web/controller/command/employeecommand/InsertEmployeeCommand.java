package com.qulix.losevsa.trainingtask.web.controller.command.employeecommand;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeFieldLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;

/**
 * Insert employee command.
 */
public class InsertEmployeeCommand implements Command {

    private static final Logger LOG = Logger.getLogger(InsertEmployeeCommand.class);

    private static final String EMPLOYEE_LIST_PATH = "/employee";
    private static final String NEW_EMPLOYEE_FORM_PATH = "/employee/new";

    private static final String FIRST_NAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String POSITION_PARAMETER = "position";

    private static final String DUPLICATES_ATTRIBUTE_NAME = "duplicates";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final Service<Employee> employeeService;

    public InsertEmployeeCommand(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Employee employee = new Employee();
            employee.setFirstName(request.getParameter(FIRST_NAME_PARAMETER));
            employee.setSurname(request.getParameter(SURNAME_PARAMETER));
            employee.setPosition(request.getParameter(POSITION_PARAMETER));

            String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
            if (patronymic != null && !patronymic.isBlank()) {
                employee.setPatronymic(patronymic);
            }

            HttpSession session = request.getSession();
            ArrayList<String> duplicates = (ArrayList<String>) session.getAttribute(DUPLICATES_ATTRIBUTE_NAME);
            if (duplicates == null) {
                employeeService.create(employee);

                duplicates = new ArrayList<>();
                duplicates.add(employee.toString());
                session.setAttribute(DUPLICATES_ATTRIBUTE_NAME, duplicates);
            }
            else if (!duplicates.contains(employee.toString())) {
                employeeService.create(employee);

                duplicates.add(employee.toString());
                session.setAttribute(DUPLICATES_ATTRIBUTE_NAME, duplicates);
            }

            response.sendRedirect(EMPLOYEE_LIST_PATH);
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
        LOG.warn("Can't create employee cause:", e);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(NEW_EMPLOYEE_FORM_PATH).forward(request, response);
    }
}
