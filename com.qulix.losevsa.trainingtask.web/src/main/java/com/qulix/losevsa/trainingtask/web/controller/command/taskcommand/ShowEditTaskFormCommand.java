package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.dto.TaskDto;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;

/**
 * Show edit task form command.
 */
public class ShowEditTaskFormCommand implements Command {

    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String PROJECT_ID_PARAMETER = "selectedProjectId";

    private static final String TASK_ID_PARAMETER = "taskId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        Service<Task, TaskDto> taskService = serviceProvider.getTaskService();

        long taskId = Long.parseLong(request.getParameter(TASK_ID_PARAMETER));
        Task task = taskService.get(taskId);
        request.setAttribute("task", task);

        Service<Project, ProjectDto> projectService = serviceProvider.getProjectService();
        String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
        if (strProjectId != null && !strProjectId.isBlank()) {
            long id = Long.parseLong(strProjectId);
            Project project = projectService.get(id);
            request.setAttribute("selectedProject", project);
        }

        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }
}
