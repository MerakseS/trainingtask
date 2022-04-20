package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.dto.TaskDto;
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

/**
 * Show task list command.
 */
public class ShowTaskListCommand implements Command {

    private final Service<Task, TaskDto> taskService;

    private static final String TASK_LIST_PATH = "/WEB-INF/jsp/taskList.jsp";

    public ShowTaskListCommand(Service<Task, TaskDto> taskService) {
        this.taskService = taskService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> taskList = taskService.getAll();

        request.setAttribute("taskList", taskList);
        request.getRequestDispatcher(TASK_LIST_PATH).forward(request, response);
    }
}
