package controller.command.impl.taskCommand;

import entity.Employee;
import entity.Project;
import entity.enums.Status;
import service.EmployeeService;
import service.ProjectService;
import service.ServiceProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowNewTaskFormCommand implements controller.command.Command {
    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String PROJECT_ID_PARAMETER = "projectId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider provider = ServiceProvider.getInstance();
        ProjectService projectService = provider.getProjectService();

//        List<Project> projectList = projectService.getAllProjects();
//        request.setAttribute("projectList", projectList);

        String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
        if (strProjectId != null && !strProjectId.isBlank()) {
            long id = Long.parseLong(strProjectId);
            Project project = projectService.getProject(id);
            request.setAttribute("selectedProject", project);
        }

//        EmployeeService employeeService = provider.getEmployeeService();
//        List<Employee> employeeList = employeeService.getAllEmployees();
//        request.setAttribute("employeeList", employeeList);
//
//        request.setAttribute("statuses", Status.values());
        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }
}
