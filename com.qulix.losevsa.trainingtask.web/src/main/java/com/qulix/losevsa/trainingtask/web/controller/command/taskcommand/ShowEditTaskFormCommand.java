package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import java.util.List;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.PageNotFoundException;

/**
 * Show edit task form command.
 */
public class ShowEditTaskFormCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowEditTaskFormCommand.class);

    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String TASK_ID_PARAMETER = "taskId";
    private static final String PROJECT_ID_PARAMETER = "projectId";
    private static final String PROJECT_NAME_PARAMETER = "projectName";
    private static final String PROJECT_DESCRIPTION_PARAMETER = "projectDescription";

    private static final String EDITED_PROJECT_ATTRIBUTE_NAME = "editedProject";
    private static final String EDITED_TASK_ATTRIBUTE_NAME = "task";
    private static final String PROJECT_LIST_ATTRIBUTE_NAME = "projectList";
    private static final String EMPLOYEE_LIST_ATTRIBUTE_NAME = "employeeList";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final Service<Task> taskService;
    private final Service<Project> projectService;
    private final Service<Employee> employeeService;

    /**
     * Instantiates a new Show edit task form command.
     * @param taskService the task service
     * @param projectService the project service
     * @param employeeService the employee service
     */
    public ShowEditTaskFormCommand(Service<Task> taskService,
        Service<Project> projectService,
        Service<Employee> employeeService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long taskId = Long.parseLong(request.getParameter(TASK_ID_PARAMETER));
            Project editedProject = setEditedProject(request);
            request.setAttribute(EDITED_TASK_ATTRIBUTE_NAME,
                editedProject == null ? taskService.get(taskId) : editedProject.getTaskList().get((int) taskId));

            List<Project> projectList = projectService.getAll();
            request.setAttribute(PROJECT_LIST_ATTRIBUTE_NAME, projectList);
            List<Employee> employeeList = employeeService.getAll();
            request.setAttribute(EMPLOYEE_LIST_ATTRIBUTE_NAME, employeeList);

            request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
        }
        catch (PageNotFoundException | NumberFormatException e) {
            LOG.warn("Can't show task form cause:", e);
            request.setAttribute(
                ERROR_ATTRIBUTE_NAME,
                format("Задача с id %s не существует!", request.getParameter(TASK_ID_PARAMETER))
            );
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }

    private Project setEditedProject(HttpServletRequest request) {
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

        return editedProject;
    }
}
