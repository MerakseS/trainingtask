package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.TaskDto;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;

/**
 * Show task list command.
 */
public class ShowTaskListCommand implements Command {

    private static final String TASK_LIST_PATH = "/WEB-INF/jsp/taskList.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        Service<Task, TaskDto> taskService = serviceProvider.getTaskService();

        List<Task> taskList = taskService.getAll();

        request.setAttribute("taskList", taskList);
        request.getRequestDispatcher(TASK_LIST_PATH).forward(request, response);
    }
}
