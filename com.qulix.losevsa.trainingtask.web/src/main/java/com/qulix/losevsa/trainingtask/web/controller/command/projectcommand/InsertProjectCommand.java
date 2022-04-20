package com.qulix.losevsa.trainingtask.web.controller.command.projectcommand;

import java.io.IOException;

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

/**
 * Insert project command.
 */
public class InsertProjectCommand implements Command {

    private static final Logger LOG = Logger.getLogger(InsertProjectCommand.class);

    private static final String PROJECT_LIST_PATH = "/project";
    private static final String NEW_PROJECT_FORM_PATH = "/project/new";

    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Repository<Project> projectRepository = new DefaultProjectRepository();
        Repository<Task> taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
        Service<Project, ProjectDto> projectService = new DefaultProjectService(projectRepository, taskRepository);

        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(request.getParameter(NAME_PARAMETER));
        projectDto.setDescription(request.getParameter(DESCRIPTION_PARAMETER));

        try {
            projectService.create(projectDto);
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
