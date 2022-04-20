package com.qulix.losevsa.trainingtask.web.controller.command.projectcommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultTaskRepository;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.DefaultProjectService;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Repository<Project> projectRepository = new DefaultProjectRepository();
        Repository<Task> taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
        Service<Project, ProjectDto> projectService = new DefaultProjectService(projectRepository, taskRepository);

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));

        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(request.getParameter(NAME_PARAMETER));
        projectDto.setDescription(request.getParameter(DESCRIPTION_PARAMETER));

        try {
            projectService.update(id, projectDto);
            response.sendRedirect(PROJECT_LIST_PATH);
        }
        catch (NotFoundException e) {
            LOG.warn(e.toString());
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("Сотрудник с id %d не существует!", id));
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
        catch (FieldNotFilledException e) {
            handleException(e.toString(), "Введите обязательные поля.", request, response);
        }
        catch (NameLengthExceededException e) {
            handleException(e.toString(), "Длина наименования должна быть не больше 30 символов.", request, response);
        }
        catch (DescriptionLengthExceededException e) {
            handleException(e.toString(), "Длина описания должна быть не больше 200 символов.", request, response);
        }
    }

    private void handleException(String logMessage, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn(logMessage);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(EDIT_PROJECT_FORM_PATH).forward(request, response);
    }
}
