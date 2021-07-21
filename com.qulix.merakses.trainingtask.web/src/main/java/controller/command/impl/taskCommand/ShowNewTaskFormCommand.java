package controller.command.impl.taskCommand;

import entity.Project;
import service.ProjectService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowNewTaskFormCommand implements controller.command.Command {
    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String PROJECT_ID_PARAMETER = "selectedProjectId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider provider = ServiceProvider.getInstance();
        ProjectService projectService = provider.getProjectService();

        String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
        if (strProjectId != null && !strProjectId.isBlank()) {
            long id = Long.parseLong(strProjectId);
            Project project = projectService.getProject(id);
            request.setAttribute("selectedProject", project);
        }

        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }
}
