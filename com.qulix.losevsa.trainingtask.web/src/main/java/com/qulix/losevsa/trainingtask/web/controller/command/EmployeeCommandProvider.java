package com.qulix.losevsa.trainingtask.web.controller.command;

import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.DeleteEmployeeCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.InsertEmployeeCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.ShowEditEmployeeFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.ShowEmployeeListCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.ShowNewEmployeeFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.UpdateEmployeeCommand;

/**
 * Provider for employee commands.
 */
public class EmployeeCommandProvider extends CommandProvider {

    /**
     * Initialize employee commands with command names
     */
    public EmployeeCommandProvider() {
        super();
        Command showListCommand = new ShowEmployeeListCommand();
        commands.put(EMPTY_COMMAND, showListCommand);
        commands.put(SHOW_LIST_COMMAND, showListCommand);
        commands.put(SHOW_NEW_FORM_COMMAND, new ShowNewEmployeeFormCommand());
        commands.put(INSERT_COMMAND, new InsertEmployeeCommand());
        commands.put(SHOW_EDIT_FORM_COMMAND, new ShowEditEmployeeFormCommand());
        commands.put(UPDATE_COMMAND, new UpdateEmployeeCommand());
        commands.put(DELETE_COMMAND, new DeleteEmployeeCommand());
    }

}
