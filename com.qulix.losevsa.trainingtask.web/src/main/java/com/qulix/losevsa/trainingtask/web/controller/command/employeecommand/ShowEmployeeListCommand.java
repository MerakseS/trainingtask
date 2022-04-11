package com.qulix.losevsa.trainingtask.web.controller.command.employeecommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.DefaultEmployeeService;
import com.qulix.losevsa.trainingtask.web.service.Service;

/**
 * The command that show employee list.
 */
public class ShowEmployeeListCommand implements Command {

    private static final String EMPLOYEE_LIST_PATH = "/WEB-INF/jsp/employeeList.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Service<Employee, EmployeeDto> employeeService = new DefaultEmployeeService(employeeRepository);

        List<Employee> employeeList = employeeService.getAll();

        request.setAttribute("employeeList", employeeList);
        request.getRequestDispatcher(EMPLOYEE_LIST_PATH).forward(request, response);
    }
}
