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
import java.util.List;

@WebServlet(name = "Controller", value = "/Controller")
public class EmployeeController extends HttpServlet {
    private static final EmployeeService employeeService = ServiceProvider.getEmployeeService();
    private static final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("id");
        if (strId == null) {
            List<Employee> employeeList = employeeService.getAllEmployees();
            sendJson(response, employeeList);
            return;
        }

        long id = Long.parseLong(strId);
        Employee employee = employeeService.getEmployee(id);
        sendJson(response, employee);
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
}
