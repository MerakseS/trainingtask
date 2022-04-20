package com.qulix.losevsa.trainingtask.web.controller.command;

import java.util.Map;

import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.DeleteProjectCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.InsertProjectCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.ShowEditProjectFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.ShowNewProjectFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.ShowProjectListCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.projectcommand.UpdateProjectCommand;

/**
 * Provider for project commands.
 */
public class ProjectCommandProvider extends CommandProvider {

    /**
     * Initialize project commands with command names
     */
    public ProjectCommandProvider() {
        super();
        Map<String, Command> commands = getCommands();
        Command showListCommand = new ShowProjectListCommand();
        commands.put(EMPTY_COMMAND, showListCommand);
        commands.put(SHOW_LIST_COMMAND, showListCommand);
        commands.put(SHOW_NEW_FORM_COMMAND, new ShowNewProjectFormCommand());
        commands.put(INSERT_COMMAND, new InsertProjectCommand());
        commands.put(SHOW_EDIT_FORM_COMMAND, new ShowEditProjectFormCommand());
        commands.put(UPDATE_COMMAND, new UpdateProjectCommand());
        commands.put(DELETE_COMMAND, new DeleteProjectCommand());
    }
}
