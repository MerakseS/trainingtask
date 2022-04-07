package com.qulix.losevsa.trainingtask.web.controller.command;

import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.DeleteTaskCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.InsertTaskCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.ShowEditTaskFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.ShowNewTaskFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.ShowTaskListCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.UpdateTaskCommand;

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
