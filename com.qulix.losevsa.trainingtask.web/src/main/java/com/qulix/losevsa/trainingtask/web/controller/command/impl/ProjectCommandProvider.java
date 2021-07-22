package com.qulix.losevsa.trainingtask.web.controller.command.impl;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.controller.command.CommandProvider;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.projectCommand.*;

/**
 * Provider for project commands.
 */
public class ProjectCommandProvider extends CommandProvider {
    /**
     * Initialize project commands with command names
     */
    public ProjectCommandProvider() {
        super();
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
