package com.qulix.losevsa.trainingtask.web.controller.command.impl;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.controller.command.CommandProvider;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand.DeleteTaskCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand.InsertTaskCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand.ShowEditTaskFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand.ShowNewTaskFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand.ShowTaskListCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand.UpdateTaskCommand;

/**
 * Provider for task commands.
 */
public class TaskCommandProvider extends CommandProvider {

    /**
     * Instantiates a new Task command provider.
     */
    public TaskCommandProvider() {
        super();
        Command showListCommand = new ShowTaskListCommand();
        commands.put(EMPTY_COMMAND, showListCommand);
        commands.put(SHOW_LIST_COMMAND, showListCommand);
        commands.put(SHOW_NEW_FORM_COMMAND, new ShowNewTaskFormCommand());
        commands.put(INSERT_COMMAND, new InsertTaskCommand());
        commands.put(SHOW_EDIT_FORM_COMMAND, new ShowEditTaskFormCommand());
        commands.put(UPDATE_COMMAND, new UpdateTaskCommand());
        commands.put(DELETE_COMMAND, new DeleteTaskCommand());
    }
}
