package com.qulix.losevsa.trainingtask.web.controller.command.impl.taskCommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.service.ServiceProvider;
import com.qulix.losevsa.trainingtask.web.service.TaskService;
import com.qulix.losevsa.trainingtask.web.service.exception.DateParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.EndDateEarlierStartDateException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NoProjectException;
import com.qulix.losevsa.trainingtask.web.service.exception.StatusParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeNegativeException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeParseException;

/**
 * Insert task command.
 */
public class InsertTaskCommand implements Command {

    private static final Logger LOG = Logger.getLogger(InsertTaskCommand.class);

    private static final String TASK_LIST_PATH = "/task";
    private static final String PROJECT_EDIT_FORM_PATH = "/project/edit?id=";
    private static final String NEW_TASK_FORM_PATH = "/task/new";

    private static final String NAME_PARAMETER = "name";
    private static final String PROJECT_ID_PARAMETER = "projectId";
    private static final String WORK_TIME_PARAMETER = "workTime";
    private static final String START_DATE_PARAMETER = "startDate";
    private static final String END_DATE_PARAMETER = "endDate";
    private static final String STATUS_PARAMETER = "status";
    private static final String EMPLOYEE_ID_PARAMETER = "employeeId";
    private static final String SELECTED_PROJECT_ID_PARAMETER = "selectedProjectId";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TaskService taskService = serviceProvider.getTaskService();

        String name = request.getParameter(NAME_PARAMETER);
        String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
        String workTime = request.getParameter(WORK_TIME_PARAMETER);
        String startDate = request.getParameter(START_DATE_PARAMETER);
        String endDate = request.getParameter(END_DATE_PARAMETER);
        String status = request.getParameter(STATUS_PARAMETER);
        String strEmployeeId = request.getParameter(EMPLOYEE_ID_PARAMETER);
        String strSelectedProjectId = request.getParameter(SELECTED_PROJECT_ID_PARAMETER);

        try {
            taskService.createTask(name, strProjectId, workTime, startDate, endDate, status, strEmployeeId);
            response.sendRedirect(strSelectedProjectId != null ?
                PROJECT_EDIT_FORM_PATH + strSelectedProjectId :
                TASK_LIST_PATH);
        }
        catch (WorkTimeParseException e) {
            handleException(e.getMessage(), "???????????????????????? ???????? ????????????.", request, response);
        }
        catch (DateParseException e) {
            handleException(e.getMessage(), "???????????????????????? ???????? ????????.", request, response);
        }
        catch (StatusParseException e) {
            handleException(e.getMessage(), "???????????????????????? ???????? ??????????????.", request, response);
        }
        catch (NoProjectException e) {
            handleException(e.getMessage(), "?????? ???????????????????? ???????????? ?????????????? ???????????????? ????????????.", request, response);
        }
        catch (FieldNotFilledException e) {
            handleException(e.getMessage(), "?????????????? ???????????????????????? ????????.", request, response);
        }
        catch (NameLengthExceededException e) {
            handleException(e.getMessage(), "?????????? ???????????????????????? ???????????? ???????? ???? ???????????? 30 ????????????????.", request, response);
        }
        catch (WorkTimeNegativeException e) {
            handleException(e.getMessage(), "?????????? ???????????? ???? ?????????? ???????? ??????????????????????????.", request, response);
        }
        catch (EndDateEarlierStartDateException e) {
            handleException(e.getMessage(), "???????? ?????????????????? ???? ?????????? ???????? ???????????? ???????? ????????????.", request, response);
        }
    }

    private void handleException(String logMessage, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn(logMessage);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(NEW_TASK_FORM_PATH).forward(request, response);
    }
}
