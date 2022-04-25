package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;

/**
 * Show task list command.
 */
public class ShowTaskListCommand implements Command {

    private static final String TASK_LIST_PATH = "/WEB-INF/jsp/taskList.jsp";

    private final Service<Task> taskService;

    /**
     * Instantiates a new Show task list command.
     *
     * @param taskService the task service
     */
    public ShowTaskListCommand(Service<Task> taskService) {
        this.taskService = taskService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> taskList = taskService.getAll();
        request.setAttribute("taskList", taskList);
        request.getRequestDispatcher(TASK_LIST_PATH).forward(request, response);
    }
}
