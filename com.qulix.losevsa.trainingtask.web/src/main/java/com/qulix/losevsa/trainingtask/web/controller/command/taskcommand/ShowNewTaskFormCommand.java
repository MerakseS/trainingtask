package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.Service;

/**
 * Show new task form command.
 */
public class ShowNewTaskFormCommand implements Command {

    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String PROJECT_ID_PARAMETER = "projectId";
    private static final String PROJECT_NAME_PARAMETER = "projectName";
    private static final String PROJECT_DESCRIPTION_PARAMETER = "projectDescription";

    private static final String EDITED_PROJECT_ATTRIBUTE_NAME = "editedProject";
    private static final String PROJECT_LIST_ATTRIBUTE_NAME = "projectList";
    private static final String EMPLOYEE_LIST_ATTRIBUTE_NAME = "employeeList";

    private final Service<Project> projectService;
    private final Service<Employee> employeeService;

    /**
     * Instantiates a new Show new task form command.
     *
     * @param projectService the project service
     * @param employeeService the employee service
     */
    public ShowNewTaskFormCommand(Service<Project> projectService, Service<Employee> employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setEditedProject(request);

        List<Project> projectList = projectService.getAll();
        request.setAttribute(PROJECT_LIST_ATTRIBUTE_NAME, projectList);
        List<Employee> employeeList = employeeService.getAll();
        request.setAttribute(EMPLOYEE_LIST_ATTRIBUTE_NAME, employeeList);

        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }

    private void setEditedProject(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Project editedProject = (Project) session.getAttribute(EDITED_PROJECT_ATTRIBUTE_NAME);
        if (editedProject != null) {
            String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
            if (strProjectId != null && !strProjectId.isBlank()) {
                long id = Long.parseLong(strProjectId);
                editedProject.setId(id);
            }

            String name = request.getParameter(PROJECT_NAME_PARAMETER);
            if (name != null && !name.isBlank()) {
                editedProject.setName(name);
            }

            String description = request.getParameter(PROJECT_DESCRIPTION_PARAMETER);
            if (description != null && !description.isBlank()) {
                editedProject.setDescription(description);
            }

            session.setAttribute(EDITED_PROJECT_ATTRIBUTE_NAME, editedProject);
        }
    }
}
