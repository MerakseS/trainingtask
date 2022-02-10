package com.qulix.losevsa.trainingtask.web.controller.command;

import java.util.HashMap;

import com.qulix.losevsa.trainingtask.web.controller.command.impl.NotFoundCommand;

/**
 * Represents the base class for command providers.
 */
public abstract class CommandProvider {

    /**
     * The constant EMPTY_COMMAND.
     */
    protected static final String EMPTY_COMMAND = null;

    /**
     * The constant SHOW_LIST_COMMAND.
     */
    protected static final String SHOW_LIST_COMMAND = "/";

    /**
     * The constant SHOW_NEW_FORM_COMMAND.
     */
    protected static final String SHOW_NEW_FORM_COMMAND = "/new";

    /**
     * The constant INSERT_COMMAND.
     */
    protected static final String INSERT_COMMAND = "/insert";

    /**
     * The constant SHOW_EDIT_FORM_COMMAND.
     */
    protected static final String SHOW_EDIT_FORM_COMMAND = "/edit";

    /**
     * The constant UPDATE_COMMAND.
     */
    protected static final String UPDATE_COMMAND = "/update";

    /**
     * The constant DELETE_COMMAND.
     */
    protected static final String DELETE_COMMAND = "/delete";

    /**
     * The Commands map that contains path as a key and command as a value.
     */
    protected final HashMap<String, Command> commands;

    /**
     * Instantiates a new Command provider.
     */
    public CommandProvider() {
        commands = new HashMap<>();
    }

    /**
     * Gets command by path.
     *
     * @param commandName the command name
     * @return the command
     */
    public Command getCommand(String commandName) {
        if (commands.containsKey(commandName)) {
            return commands.get(commandName);
        }
        else {
            return new NotFoundCommand();
        }
    }
}
