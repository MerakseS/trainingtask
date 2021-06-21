package controller.command.impl.employeeCommand;

import controller.command.Command;
import entity.Employee;
import service.EmployeeService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowEditEmployeeFormCommand implements Command {
    private static final String EMPLOYEE_EDIT_PATH = "/WEB-INF/jsp/employeeEdit.jsp";

    private static final String ID_PARAMETER = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        EmployeeService employeeService = serviceProvider.getEmployeeService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        Employee employee = employeeService.getEmployee(id);

        request.setAttribute("employee", employee);
        request.getRequestDispatcher(EMPLOYEE_EDIT_PATH).forward(request, response);
    }
}
