package controller.command.impl.taskCommand;

import controller.command.Command;
import entity.Task;
import service.ServiceProvider;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Show task list command.
 */
public class ShowTaskListCommand implements Command {
    private static final String TASK_LIST_PATH = "/WEB-INF/jsp/taskList.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TaskService taskService = serviceProvider.getTaskService();

        List<Task> taskList = taskService.getAllTasks();

        request.setAttribute("taskList", taskList);
        request.getRequestDispatcher(TASK_LIST_PATH).forward(request, response);
    }
}
