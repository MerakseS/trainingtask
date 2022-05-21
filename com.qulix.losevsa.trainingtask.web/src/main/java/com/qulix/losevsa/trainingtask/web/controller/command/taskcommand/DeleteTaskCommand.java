package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.PageNotFoundException;

/**
 * Delete task command.
 */
public class DeleteTaskCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteTaskCommand.class);

    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "taskId";
    private static final String PROJECT_ID_PARAMETER = "projectId";
    private static final String PROJECT_NAME_PARAMETER = "projectName";
    private static final String PROJECT_DESCRIPTION_PARAMETER = "projectDescription";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";
    private static final String EDITED_PROJECT_ATTRIBUTE_NAME = "editedProject";

    private final Service<Task> taskService;

    /**
     * Instantiates a new Delete task command.
     *
     * @param taskService the task service
     */
    public DeleteTaskCommand(Service<Task> taskService) {
        this.taskService = taskService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            long id = Long.parseLong(request.getParameter(ID_PARAMETER));

            Project editedProject = setEditedProject(request);

            if (editedProject == null) {
                taskService.delete(id);
            }
            else {
                editedProject.getTaskList().remove((int) id);
                request.getSession().setAttribute(EDITED_PROJECT_ATTRIBUTE_NAME, editedProject);
            }

            String referer = request.getHeader("Referer");
            response.sendRedirect(referer);
        }
        catch (PageNotFoundException | NumberFormatException e) {
            LOG.warn("Can't delete task cause:", e);
            request.setAttribute(
                ERROR_ATTRIBUTE_NAME,
                format("Задача с id %s не существует!", request.getParameter(ID_PARAMETER))
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
