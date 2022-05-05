package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.DateParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.EndDateEarlierStartDateException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NoProjectException;
import com.qulix.losevsa.trainingtask.web.service.exception.NotFoundException;
import com.qulix.losevsa.trainingtask.web.service.exception.TaskStatusParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeNegativeException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeParseException;
import com.qulix.losevsa.trainingtask.web.utils.ParseUtils;

/**
 * Update task command.
 */
public class UpdateTaskCommand implements Command {

    private static final Logger LOG = Logger.getLogger(UpdateTaskCommand.class);

    private static final String TASK_LIST_PATH = "/task";
    private static final String PROJECT_EDIT_FORM_PATH = "/project/edit?id=";
    private static final String EDIT_TASK_FORM_PATH = "/task/edit";
    private static final String NOT_FOUND_PATH = "/WEB-INF/jsp/notFoundPage.jsp";

    private static final String ID_PARAMETER = "taskId";
    private static final String NAME_PARAMETER = "name";
    private static final String PROJECT_ID_PARAMETER = "projectId";
    private static final String WORK_TIME_PARAMETER = "workTime";
    private static final String START_DATE_PARAMETER = "startDate";
    private static final String END_DATE_PARAMETER = "endDate";
    private static final String TASK_STATUS_PARAMETER = "taskStatus";
    private static final String EMPLOYEE_ID_PARAMETER = "employeeId";
    private static final String SELECTED_PROJECT_ID_PARAMETER = "selectedProjectId";

    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final Service<Task> taskService;
    private final ParseUtils parseUtils;

    /**
     * Instantiates a new Update task command.
     *
     * @param taskService the task service
     * @param parseUtils the parse utils
     */
    public UpdateTaskCommand(Service<Task> taskService, ParseUtils parseUtils) {
        this.taskService = taskService;
        this.parseUtils = parseUtils;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Task task = new Task();
            task.setId(Long.parseLong(request.getParameter(ID_PARAMETER)));
            task.setName(request.getParameter(NAME_PARAMETER));
            task.setProject(parseUtils.parseProject(request.getParameter(PROJECT_ID_PARAMETER)));
            task.setWorkTime(parseUtils.parseInteger(request.getParameter(WORK_TIME_PARAMETER)));
            task.setStartDate(parseUtils.parseDate(request.getParameter(START_DATE_PARAMETER)));
            task.setEndDate(parseUtils.parseDate(request.getParameter(END_DATE_PARAMETER)));
            task.setTaskStatus(parseUtils.parseTaskStatus(request.getParameter(TASK_STATUS_PARAMETER)));
            task.setEmployee(parseUtils.parseEmployee(request.getParameter(EMPLOYEE_ID_PARAMETER)));

            String strSelectedProjectId = request.getParameter(SELECTED_PROJECT_ID_PARAMETER);

            taskService.update(task);
            response.sendRedirect(strSelectedProjectId != null ?
                PROJECT_EDIT_FORM_PATH + strSelectedProjectId :
                TASK_LIST_PATH);
        }
        catch (NotFoundException | NumberFormatException e) {
            LOG.warn("Can't update task cause:", e);
            request.setAttribute(
                ERROR_ATTRIBUTE_NAME,
                format("Задача с id %s не существует!", request.getParameter(ID_PARAMETER))
            );
            request.getRequestDispatcher(NOT_FOUND_PATH).forward(request, response);
        }
        catch (WorkTimeParseException e) {
            handleException(e, "Некорректный ввод работы.", request, response);
        }
        catch (DateParseException e) {
            handleException(e, "Некорректный ввод даты.", request, response);
        }
        catch (TaskStatusParseException e) {
            handleException(e, "Некорректный ввод статуса.", request, response);
        }
        catch (NoProjectException e) {
            handleException(e, "Для добавления задачи сначала создайте проект.", request, response);
        }
        catch (FieldNotFilledException e) {
            handleException(e, "Введите обязательные поля.", request, response);
        }
        catch (NameLengthExceededException e) {
            handleException(e, "Длина наименования должна быть не больше 50 символов.", request, response);
        }
        catch (WorkTimeNegativeException e) {
            handleException(e, "Время работы не может быть отрицательным.", request, response);
        }
        catch (EndDateEarlierStartDateException e) {
            handleException(e, "Дата окончания не может быть раньше даты начала.", request, response);
        }
    }

    private void handleException(Exception e, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn("Can't update task cause:", e);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(EDIT_TASK_FORM_PATH).forward(request, response);
    }
}
