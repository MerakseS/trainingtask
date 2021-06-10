package controller;

import com.google.gson.Gson;
import entity.Employee;
import service.EmployeeService;
import service.ServiceProvider;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Employee", value = "/employee/*")
public class EmployeeController extends HttpServlet {
    private static final EmployeeService employeeService = ServiceProvider.getEmployeeService();
    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            try {
                List<Employee> employeeList = employeeService.getAllEmployees();
                sendJson(response, employeeList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        Long id = parseRouteParameter(request, response);
        if (id != null) {
            try {
                Employee employee = employeeService.getEmployee(id);
                sendJson(response, employee);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    private Long parseRouteParameter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }

        String[] splits = pathInfo.split("/");
        if (splits.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }

        try {
            return Long.parseLong(splits[1]);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}
