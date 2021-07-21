package controller.command.impl.projectCommand;

import controller.command.Command;
import service.ProjectService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Delete project command.
 */
public class DeleteProjectCommand implements Command {
    private static final String PROJECT_LIST_PATH = "/project";

    private static final String ID_PARAMETER = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        projectService.deleteProject(id);

        response.sendRedirect(PROJECT_LIST_PATH);
    }
}
