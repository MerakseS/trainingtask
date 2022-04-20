package com.qulix.losevsa.trainingtask.web.controller.command;

import java.util.Map;

import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.DeleteEmployeeCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.InsertEmployeeCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.ShowEditEmployeeFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.ShowEmployeeListCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.ShowNewEmployeeFormCommand;
import com.qulix.losevsa.trainingtask.web.controller.command.employeecommand.UpdateEmployeeCommand;
import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.service.Service;

/**
 * Provider for employee commands.
 */
public class EmployeeCommandProvider extends CommandProvider {

    /**
     * Initialize employee commands with command names
     * @param employeeService the service for {@link Employee}
     */
    public EmployeeCommandProvider(Service<Employee, EmployeeDto> employeeService) {
        super();
        Map<String, Command> commands = getCommands();
        Command showListCommand = new ShowEmployeeListCommand(employeeService);

        commands.put(EMPTY_COMMAND, showListCommand);
        commands.put(SHOW_LIST_COMMAND, showListCommand);
        commands.put(SHOW_NEW_FORM_COMMAND, new ShowNewEmployeeFormCommand());
        commands.put(INSERT_COMMAND, new InsertEmployeeCommand(employeeService));
        commands.put(SHOW_EDIT_FORM_COMMAND, new ShowEditEmployeeFormCommand(employeeService));
        commands.put(UPDATE_COMMAND, new UpdateEmployeeCommand(employeeService));
        commands.put(DELETE_COMMAND, new DeleteEmployeeCommand(employeeService));
    }

}
