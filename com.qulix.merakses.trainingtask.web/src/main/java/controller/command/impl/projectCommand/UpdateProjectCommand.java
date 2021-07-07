package controller.command.impl.projectCommand;

import controller.command.Command;
import service.ProjectService;
import service.ServiceException;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateProjectCommand implements Command {
    private static final String PROJECT_LIST_PATH = "/project";
    private static final String EDIT_PROJECT_FORM_PATH = "/project/edit";

    private static final String ID_PARAMETER = "id";
    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ProjectService projectService = serviceProvider.getProjectService();

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));
        String name = request.getParameter(NAME_PARAMETER);
        String description = request.getParameter(DESCRIPTION_PARAMETER);

        try {
            projectService.updateProject(id, name, description);
            response.sendRedirect(PROJECT_LIST_PATH);
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(EDIT_PROJECT_FORM_PATH).forward(request, response);
        }
    }
}
