package controller;

import controller.utils.ServletUtils;
import entity.Employee;
import service.EmployeeService;
import service.ServiceException;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Employee", value = "/employee/*")
public class EmployeeController extends HttpServlet {

    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";

    private static final EmployeeService employeeService = ServiceProvider.getEmployeeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Employee> employeeList = employeeService.getAllEmployees();
                ServletUtils.sendJson(response, employeeList);
                return;
            }

            Long id = ServletUtils.parseRouteParameter(pathInfo);
            Employee employee = employeeService.getEmployee(id);
            ServletUtils.sendJson(response, employee);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String firsName = request.getParameter(FIRSTNAME_PARAMETER);
            String surname = request.getParameter(SURNAME_PARAMETER);
            String patronymic = request.getParameter(PATRONYMIC_PARAMETER);

            Employee employee = employeeService.createEmployee(firsName, surname, patronymic);
            ServletUtils.sendJson(response, employee);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = ServletUtils.parseRouteParameter(request.getPathInfo());
            String firsName = request.getParameter(FIRSTNAME_PARAMETER);
            String surname = request.getParameter(SURNAME_PARAMETER);
            String patronymic = request.getParameter(PATRONYMIC_PARAMETER);

            Employee employee = employeeService.updateEmployee(id, firsName, surname, patronymic);
            ServletUtils.sendJson(response, employee);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = ServletUtils.parseRouteParameter(request.getPathInfo());
            employeeService.deleteEmployee(id);
            ServletUtils.sendJson(response, id);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
        }
    }
}
