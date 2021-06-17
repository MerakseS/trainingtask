package controller.command.impl.employeeCommand;

import controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowNewEmployeeFormCommand implements Command {
    private static final String EMPLOYEE_EDIT_PATH = "/WEB-INF/jsp/employeeEdit.jsp";
    private static final String TITLE_VALUE = "Добавить работника";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", TITLE_VALUE);
        request.getRequestDispatcher(EMPLOYEE_EDIT_PATH).forward(request, response);
    }
}
