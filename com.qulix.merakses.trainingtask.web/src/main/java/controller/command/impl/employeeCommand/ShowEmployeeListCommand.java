package controller.command.impl.employeeCommand;

import controller.command.Command;
import entity.Employee;
import service.EmployeeService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The command that show employee list.
 */
public class ShowEmployeeListCommand implements Command {
    private static final String EMPLOYEE_LIST_PATH = "/WEB-INF/jsp/employeeList.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        List<Employee> employeeList = employeeService.getAllEmployees();

        request.setAttribute("employeeList", employeeList);
        request.getRequestDispatcher(EMPLOYEE_LIST_PATH).forward(request, response);
    }
}
