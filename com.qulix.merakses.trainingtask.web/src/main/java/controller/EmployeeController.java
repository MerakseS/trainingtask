package controller;

import com.google.gson.Gson;
import entity.Employee;
import service.EmployeeService;
import service.ServiceException;
import service.ServiceProvider;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.security.Provider;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Employee", value = "/employee/*")
public class EmployeeController extends HttpServlet {
    private static final EmployeeService employeeService = ServiceProvider.getEmployeeService();
    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Employee> employeeList = employeeService.getAllEmployees();
                sendJson(response, employeeList);
                return;
            }

            Long id = parseRouteParameter(pathInfo);
            Employee employee = employeeService.getEmployee(id);
            sendJson(response, employee);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            response.getWriter().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void sendJson(HttpServletResponse response, Object payload) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(payload));
        writer.flush();
    }

    private Long parseRouteParameter(String pathInfo) {
        String[] splits = pathInfo.split("/");
        return Long.parseLong(splits[1]);
    }
}
