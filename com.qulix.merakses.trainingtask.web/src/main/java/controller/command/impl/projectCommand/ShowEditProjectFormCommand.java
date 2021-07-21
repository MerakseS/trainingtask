package controller.command.impl.projectCommand;

import controller.command.Command;
import entity.Project;
import service.ProjectService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Show edit project form command.
 */
public class ShowEditProjectFormCommand implements Command {
    private static final String PROJECT_EDIT_PATH = "/WEB-INF/jsp/projectEdit.jsp";

    private static final String ID_PARAMETER = "id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        Project project = projectService.getProject(id);

        request.setAttribute("project", project);
        request.getRequestDispatcher(PROJECT_EDIT_PATH).forward(request, response);
    }
}
