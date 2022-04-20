package com.qulix.losevsa.trainingtask.web.controller.command.employeecommand;

import java.io.IOException;
import static java.lang.String.format;

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
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Delete employee command.
 */
public class DeleteEmployeeCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteEmployeeCommand.class);

    private static final String ID_PARAMETER = "id";

    private static final String EMPLOYEE_LIST_PATH = "/employee";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Service<Employee, EmployeeDto> employeeService = new DefaultEmployeeService(employeeRepository);

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));

        try {
            employeeService.delete(id);
            response.sendRedirect(EMPLOYEE_LIST_PATH);
        }
        catch (NotFoundException e) {
            LOG.warn("Can't delete employee cause:", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("Сотрудник с id %d не существует!", id));
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
