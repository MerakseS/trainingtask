package controller.command.impl.taskCommand;

import entity.Employee;
import entity.Project;
import entity.Task;
import entity.enums.Status;
import service.EmployeeService;
import service.ProjectService;
import service.ServiceProvider;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowEditTaskFormCommand implements controller.command.Command {
    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String PROJECT_ID_PARAMETER = "projectId";

    private static final String TASK_ID_PARAMETER = "taskId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();

        TaskService taskService = serviceProvider.getTaskService();
        long taskId = Long.parseLong(request.getParameter(TASK_ID_PARAMETER));
        Task task = taskService.getTask(taskId);
        request.setAttribute("task", task);

        ProjectService projectService = serviceProvider.getProjectService();
        String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
        if (strProjectId != null && !strProjectId.isBlank()) {
            long id = Long.parseLong(strProjectId);
            Project project = projectService.getProject(id);
            request.setAttribute("selectedProject", project);
        }

        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }
}
