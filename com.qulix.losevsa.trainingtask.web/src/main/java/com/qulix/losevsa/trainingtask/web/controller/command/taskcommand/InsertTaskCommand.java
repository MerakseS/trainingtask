package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.service.Service;
import com.qulix.losevsa.trainingtask.web.service.exception.DateParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.EmployeeIdParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.EndDateEarlierStartDateException;
import com.qulix.losevsa.trainingtask.web.service.exception.FieldNotFilledException;
import com.qulix.losevsa.trainingtask.web.service.exception.NameLengthExceededException;
import com.qulix.losevsa.trainingtask.web.service.exception.NoProjectException;
import com.qulix.losevsa.trainingtask.web.service.exception.TaskStatusParseException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeNegativeException;
import com.qulix.losevsa.trainingtask.web.service.exception.WorkTimeParseException;
import com.qulix.losevsa.trainingtask.web.utils.ParseUtils;

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
    private static final String TASK_STATUS_PARAMETER = "taskStatus";
    private static final String EMPLOYEE_ID_PARAMETER = "employeeId";
    private static final String SELECTED_PROJECT_ID_PARAMETER = "selectedProjectId";

    private static final String DUPLICATES_ATTRIBUTE_NAME = "duplicates";
    private static final String ERROR_ATTRIBUTE_NAME = "errorMessage";

    private final Service<Task> taskService;
    private final ParseUtils parseUtils;

    /**
     * Instantiates a new Insert task command.
     *
     * @param taskService the task service
     * @param parseUtils the parse utils
     */
    public InsertTaskCommand(Service<Task> taskService, ParseUtils parseUtils) {
        this.taskService = taskService;
        this.parseUtils = parseUtils;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Task task = new Task();
            task.setName(request.getParameter(NAME_PARAMETER));
            task.setProject(parseUtils.parseProject(request.getParameter(PROJECT_ID_PARAMETER)));
            task.setWorkTime(parseUtils.parseInteger(request.getParameter(WORK_TIME_PARAMETER)));
            task.setStartDate(parseUtils.parseDate(request.getParameter(START_DATE_PARAMETER)));
            task.setEndDate(parseUtils.parseDate(request.getParameter(END_DATE_PARAMETER)));
            task.setTaskStatus(parseUtils.parseTaskStatus(request.getParameter(TASK_STATUS_PARAMETER)));
            task.setEmployee(parseUtils.parseEmployee(request.getParameter(EMPLOYEE_ID_PARAMETER)));

            String strSelectedProjectId = request.getParameter(SELECTED_PROJECT_ID_PARAMETER);

            HttpSession session = request.getSession();
            ArrayList<String> duplicates = (ArrayList<String>) session.getAttribute(DUPLICATES_ATTRIBUTE_NAME);
            if (duplicates == null) {
                taskService.create(task);

                duplicates = new ArrayList<>();
                duplicates.add(task.toString());
                session.setAttribute(DUPLICATES_ATTRIBUTE_NAME, duplicates);
            }
            else if (!duplicates.contains(task.toString())) {
                taskService.create(task);

                duplicates.add(task.toString());
                session.setAttribute(DUPLICATES_ATTRIBUTE_NAME, duplicates); //update the variable
            }

            response.sendRedirect(strSelectedProjectId != null ?
                PROJECT_EDIT_FORM_PATH + strSelectedProjectId :
                TASK_LIST_PATH);
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
        catch (EmployeeIdParseException e) {
            handleException(e, "Некорректный ввод id сотрудника.", request, response);
        }
    }

    private void handleException(Exception e, String clientMessage,
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.warn("Can't create task cause:", e);
        request.setAttribute(ERROR_ATTRIBUTE_NAME, clientMessage);
        request.getRequestDispatcher(NEW_TASK_FORM_PATH).forward(request, response);
    }
}
