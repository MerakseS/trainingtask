package com.qulix.losevsa.trainingtask.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.controller.command.TaskCommandProvider;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultTaskRepository;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.DefaultEmployeeService;
import com.qulix.losevsa.trainingtask.web.service.DefaultProjectService;
import com.qulix.losevsa.trainingtask.web.service.DefaultTaskService;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.utils.ParseUtils;

/**
 * The Controller for task.
 */
@WebServlet(name = "TaskController", value = "/task/*")
public class TaskController extends HttpServlet {

    private final TaskCommandProvider taskCommandProvider;

    /**
     * Instantiates a new Task controller.
     */
    public TaskController() {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Repository<Project> projectRepository = new DefaultProjectRepository();
        Repository<Task> taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);

        Service<Employee> employeeService = new DefaultEmployeeService(employeeRepository);
        Service<Task> taskService = new DefaultTaskService(taskRepository);
        Service<Project> projectService = new DefaultProjectService(projectRepository, taskService);

        ParseUtils parseUtils = new ParseUtils(employeeService, projectService);
        taskCommandProvider = new TaskCommandProvider(taskService, projectService, employeeService, parseUtils);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandName = request.getPathInfo();
        Command command = taskCommandProvider.getCommand(commandName);
        command.execute(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}