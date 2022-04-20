package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.dto.TaskDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultTaskRepository;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.DefaultEmployeeService;
import com.qulix.losevsa.trainingtask.web.service.DefaultProjectService;
import com.qulix.losevsa.trainingtask.web.service.DefaultTaskService;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;

/**
 * Show edit task form command.
 */
public class ShowEditTaskFormCommand implements Command {

    private static final Logger LOG = Logger.getLogger(ShowEditTaskFormCommand.class);

    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String TASK_ID_PARAMETER = "taskId";
    private static final String PROJECT_ID_PARAMETER = "selectedProjectId";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Service<Employee, EmployeeDto> employeeService = new DefaultEmployeeService(employeeRepository);
        Repository<Project> projectRepository = new DefaultProjectRepository();
        Repository<Task> taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
        Service<Project, ProjectDto> projectService = new DefaultProjectService(projectRepository, taskRepository);
        Service<Task, TaskDto> taskService = new DefaultTaskService(employeeService, projectService, taskRepository);

        long taskId = Long.parseLong(request.getParameter(TASK_ID_PARAMETER));

        try {
            Task task = taskService.get(taskId);
            request.setAttribute("task", task);

            String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
            if (strProjectId != null && !strProjectId.isBlank()) {
                long id = Long.parseLong(strProjectId);
                Project project = projectService.get(id);
                request.setAttribute("selectedProject", project);
            }

            request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
        }
        catch (NotFoundException e) {
            LOG.warn("Can't show task form cause:", e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("Сотрудник с id %d не существует!", taskId));
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
    }
}
