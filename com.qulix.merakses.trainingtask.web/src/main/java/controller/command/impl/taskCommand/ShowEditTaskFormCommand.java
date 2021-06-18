package controller.command.impl.taskCommand;

import entity.Task;
import service.ServiceProvider;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowEditTaskFormCommand implements controller.command.Command {
    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String TITLE_VALUE = "Изменить задачу";

    private static final String ID_PARAMETER = "taskId";
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TaskService taskService = serviceProvider.getTaskService();

        long taskId = Long.parseLong(request.getParameter(ID_PARAMETER));
        Task task = taskService.getTask(taskId);

        request.setAttribute("title", TITLE_VALUE);
        request.setAttribute("task", task);
        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }
}
