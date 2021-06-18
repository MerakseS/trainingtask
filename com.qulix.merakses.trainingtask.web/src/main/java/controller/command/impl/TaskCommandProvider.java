package controller.command.impl;

import controller.command.Command;
import controller.command.CommandProvider;
import controller.command.impl.taskCommand.*;

public class TaskCommandProvider extends CommandProvider {

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
