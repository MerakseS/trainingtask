package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Delete task command.
 */
public class DeleteTaskCommand implements Command {

    private static final Logger LOG = Logger.getLogger(DeleteTaskCommand.class);

    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "taskId";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

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
            taskService.delete(id);
            String referer = request.getHeader("Referer");
            response.sendRedirect(referer);
        }
        catch (NotFoundException | NumberFormatException e) {
            LOG.warn("Can't delete task cause:", e);
            request.setAttribute(
                ERROR_ATTRIBUTE_NAME,
                format("Задача с id %s не существует!", request.getParameter(ID_PARAMETER))
            );
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }

    }
}
