package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * Show new task form command.
 */
public class ShowNewTaskFormCommand implements Command {

    private final Service<Project, ProjectDto> projectService;

    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String PROJECT_ID_PARAMETER = "selectedProjectId";

    public ShowNewTaskFormCommand(Service<Project, ProjectDto> projectService) {
        this.projectService = projectService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
        if (strProjectId != null && !strProjectId.isBlank()) {
            long id = Long.parseLong(strProjectId);
            Project project = projectService.get(id);
            request.setAttribute("selectedProject", project);
        }

        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }
}
