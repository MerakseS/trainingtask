package com.qulix.losevsa.trainingtask.web.controller.command.projectcommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.DescriptionLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Update project command.
 */
public class UpdateProjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateProjectCommand.class);

    private static final String PROJECT_LIST_PATH = "/project";
    private static final String EDIT_PROJECT_FORM_PATH = "/project/edit";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "id";
    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final Service<Project> projectService;

    /**
     * Instantiates a new Update project command.
     *
     * @param projectService the project service
     */
    public UpdateProjectCommand(Service<Project> projectService) {
        this.projectService = projectService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Project project = new Project();
            project.setId(Long.parseLong(request.getParameter(ID_PARAMETER)));
            project.setName(request.getParameter(NAME_PARAMETER));

            String description = request.getParameter(DESCRIPTION_PARAMETER);
            if (description != null && !description.isBlank()) {
                project.setDescription(description);
            }

            projectService.update(project);
            response.sendRedirect(PROJECT_LIST_PATH);
        }
        catch (NotFoundException e) {
            LOG.warn("Can't update project cause:", e);
            request.setAttribute(
                ERROR_ATTRIBUTE_NAME,
                format("Проект с id %s не существует!", request.getParameter(ID_PARAMETER))
            );
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
        catch (FieldNotFilledException e) {
            handleException(e, "Введите обязательные поля.", request, response);
        }
        catch (NameLengthExceededException e) {
            handleException(e, "Длина наименования должна быть не больше 30 символов.", request, response);
        }
        catch (DescriptionLengthExceededException e) {
            handleException(e, "Длина описания должна быть не больше 200 символов.", request, response);
        }
    }

    private void handleException(Exception e, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn("Can't update project cause:", e);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(EDIT_PROJECT_FORM_PATH).forward(request, response);
    }
}
