package com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;
import com.qulix.losevsa.trainingtask.web.service.TaskService;

/**
 * The type Delete task command.
 */
public class DeleteTaskCommand implements com.qulix.losevsa.trainingtask.web.controller.command.Command {

    private static final String ID_PARAMETER = "taskId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TaskService taskService = serviceProvider.getTaskService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        taskService.deleteTask(id);

        String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }
}
