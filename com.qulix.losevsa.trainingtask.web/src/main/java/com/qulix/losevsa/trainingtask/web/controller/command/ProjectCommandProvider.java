package com.qulix.losevsa.trainingtask.web.controller.command;

import java.util.Map;

import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.DeleteProjectCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.InsertProjectCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.ShowEditProjectFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.ShowNewProjectFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.ShowProjectListCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.UpdateProjectCommand;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.service.Service;

/**
 * Provider for project commands.
 */
public class ProjectCommandProvider extends CommandProvider {

    /**
     * Initialize project commands with command names
     * @param projectService the service for the {@link Project}
     */
    public ProjectCommandProvider(Service<Project> projectService) {
        super();
        Map<String, Command> commands = getCommands();
        Command showListCommand = new ShowProjectListCommand(projectService);

        commands.put(EMPTY_COMMAND, showListCommand);
        commands.put(SHOW_LIST_COMMAND, showListCommand);
        commands.put(SHOW_NEW_FORM_COMMAND, new ShowNewProjectFormCommand());
        commands.put(INSERT_COMMAND, new InsertProjectCommand(projectService));
        commands.put(SHOW_EDIT_FORM_COMMAND, new ShowEditProjectFormCommand(projectService));
        commands.put(UPDATE_COMMAND, new UpdateProjectCommand(projectService));
        commands.put(DELETE_COMMAND, new DeleteProjectCommand(projectService));
    }
}
