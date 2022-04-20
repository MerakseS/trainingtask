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
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Show edit project form command.
 */
public class ShowEditProjectFormCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowEditProjectFormCommand.class);

    private static final String PROJECT_EDIT_PATH = "/WEB-INF/jsp/projectEdit.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "id";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Repository<Project> projectRepository = new DefaultProjectRepository();
        Repository<Task> taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
        Service<Project, ProjectDto> projectService = new DefaultProjectService(projectRepository, taskRepository);

        long id = Long.parseLong(request.getParameter(ID_PARAMETER));

        try {
            Project project = projectService.get(id);
            request.setAttribute("project", project);
            request.getRequestDispatcher(PROJECT_EDIT_PATH).forward(request, response);
        }
        catch (NotFoundException e) {
            LOG.warn("Can't show project form cause:", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("Сотрудник с id %d не существует!", id));
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
