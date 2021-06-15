package controller.command;

import controller.command.impl.NotFoundCommand;

import java.util.HashMap;

public class CommandProvider {

    protected final HashMap<String, Command> commands;

    protected static final String EMPTY_COMMAND = null;
    protected static final String SHOW_LIST_COMMAND = "/";
    protected static final String SHOW_NEW_FORM_COMMAND = "/new";
    protected static final String INSERT_COMMAND = "/insert";
    protected static final String SHOW_EDIT_FORM_COMMAND = "/edit";
    protected static final String UPDATE_COMMAND = "/update";
    protected static final String DELETE_COMMAND = "/delete";

    public CommandProvider() {
        commands = new HashMap<>();
    }

    public Command getCommand(String commandName) {
        if (commands.containsKey(commandName)) {
            return commands.get(commandName);
        } else {
            return new NotFoundCommand();
        }
    }

}
