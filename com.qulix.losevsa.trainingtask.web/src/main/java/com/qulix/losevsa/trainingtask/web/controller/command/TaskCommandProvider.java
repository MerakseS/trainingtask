package com.qulix.losevsa.trainingtask.web.controller.command;

import java.util.Map;

import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.DeleteTaskCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.InsertTaskCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.ShowEditTaskFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.ShowNewTaskFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.ShowTaskListCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.taskcommand.UpdateTaskCommand;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.utils.ParseUtils;

/**
 * Provider for task commands.
 */
public class TaskCommandProvider extends CommandProvider {

    /**
     * Instantiates a new Task command provider.
     * @param taskService the service for the {@link Task}
     * @param projectService the service for the {@link Project}
     * @param parseUtils the parse utils for the {@link Task}
     */
    public TaskCommandProvider(Service<Task> taskService, Service<Project> projectService, ParseUtils parseUtils) {
        super();
        Map<String, Command> commands = getCommands();
        Command showListCommand = new ShowTaskListCommand(taskService);
        commands.put(EMPTY_COMMAND, showListCommand);
        commands.put(SHOW_LIST_COMMAND, showListCommand);
        commands.put(SHOW_NEW_FORM_COMMAND, new ShowNewTaskFormCommand(projectService));
        commands.put(INSERT_COMMAND, new InsertTaskCommand(taskService, parseUtils));
        commands.put(SHOW_EDIT_FORM_COMMAND, new ShowEditTaskFormCommand(taskService, projectService));
        commands.put(UPDATE_COMMAND, new UpdateTaskCommand(taskService, parseUtils));
        commands.put(DELETE_COMMAND, new DeleteTaskCommand(taskService));
    }
}
