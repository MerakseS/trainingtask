package com.qulix.losevsa.trainingtask.web.controller.command.projectcommand;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.DescriptionLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;

/**
 * Insert project command.
 */
public class InsertProjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(InsertProjectCommand.class);

    private static final String PROJECT_LIST_PATH = "/project";
    private static final String NEW_PROJECT_FORM_PATH = "/project/new";

    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";

    private static final String DUPLICATES_ATTRIBUTE_NAME = "duplicates";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final Service<Project> projectService;

    /**
     * Instantiates a new Insert project command.
     *
     * @param projectService the project service
     */
    public InsertProjectCommand(Service<Project> projectService) {
        this.projectService = projectService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Project project = new Project();
            project.setName(request.getParameter(NAME_PARAMETER));

            String description = request.getParameter(DESCRIPTION_PARAMETER);
            if (description != null && !description.isBlank()) {
                project.setDescription(description);
            }

            HttpSession session = request.getSession();
            ArrayList<String> duplicates = (ArrayList<String>) session.getAttribute(DUPLICATES_ATTRIBUTE_NAME);
            if (duplicates == null) {
                projectService.create(project);

                duplicates = new ArrayList<>();
                duplicates.add(project.toString());
                session.setAttribute(DUPLICATES_ATTRIBUTE_NAME, duplicates);
            }
            else if (!duplicates.contains(project.toString())) {
                projectService.create(project);

                duplicates.add(project.toString());
                session.setAttribute(DUPLICATES_ATTRIBUTE_NAME, duplicates);
            }

            response.sendRedirect(PROJECT_LIST_PATH);
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
        LOG.warn("Can't create project cause:", e);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(NEW_PROJECT_FORM_PATH).forward(request, response);
    }
}
