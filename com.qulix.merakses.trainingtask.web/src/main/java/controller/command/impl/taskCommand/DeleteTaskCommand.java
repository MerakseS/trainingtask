package controller.command.impl.taskCommand;

import service.ServiceProvider;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTaskCommand implements controller.command.Command {
    private static final String TASK_LIST_PATH = "/task";

    private static final String ID_PARAMETER = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TaskService taskService = serviceProvider.getTaskService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        taskService.deleteTask(id);

        response.sendRedirect(TASK_LIST_PATH);
    }
}
