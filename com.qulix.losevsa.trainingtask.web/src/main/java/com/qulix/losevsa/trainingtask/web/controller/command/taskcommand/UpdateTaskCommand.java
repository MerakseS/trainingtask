package com.qulix.losevsa.trainingtask.web.controller.command.taskcommand;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;
import com.qulix.losevsa.trainingtask.web.dto.EmployeeDto;
import com.qulix.losevsa.trainingtask.web.dto.ProjectDto;
import com.qulix.losevsa.trainingtask.web.dto.TaskDto;
import com.qulix.losevsa.trainingtask.web.entity.Employee;
import com.qulix.losevsa.trainingtask.web.entity.Project;
import com.qulix.losevsa.trainingtask.web.entity.Task;
import com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultProjectRepository;
import com.qulix.losevsa.trainingtask.web.repository.DefaultTaskRepository;
import com.qulix.losevsa.trainingtask.web.repository.Repository;
import com.qulix.losevsa.trainingtask.web.service.DefaultEmployeeService;
import com.qulix.losevsa.trainingtask.web.service.DefaultProjectService;
import com.qulix.losevsa.trainingtask.web.service.DefaultTaskService;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
        Service<Employee, EmployeeDto> employeeService = new DefaultEmployeeService(employeeRepository);
        Repository<Project> projectRepository = new DefaultProjectRepository();
        Repository<Task> taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
        Service<Project, ProjectDto> projectService = new DefaultProjectService(projectRepository, taskRepository);
        Service<Task, TaskDto> taskService = new DefaultTaskService(employeeService, projectService, taskRepository);

        long taskId = Long.parseLong(request.getParameter(ID_PARAMETER));

        TaskDto taskDto = new TaskDto();
        taskDto.setName(request.getParameter(NAME_PARAMETER));
        taskDto.setProjectId(request.getParameter(PROJECT_ID_PARAMETER));
        taskDto.setWorkTime(request.getParameter(WORK_TIME_PARAMETER));
        taskDto.setStartDate(request.getParameter(START_DATE_PARAMETER));
        taskDto.setEndDate(request.getParameter(END_DATE_PARAMETER));
        taskDto.setTaskStatus(request.getParameter(TASK_STATUS_PARAMETER));
        taskDto.setEmployeeId(request.getParameter(EMPLOYEE_ID_PARAMETER));

        String strSelectedProjectId = request.getParameter(SELECTED_PROJECT_ID_PARAMETER);

        try {
            taskService.update(taskId, taskDto);
            response.sendRedirect(strSelectedProjectId != null ?
                PROJECT_EDIT_FORM_PATH + strSelectedProjectId :
                TASK_LIST_PATH);
        }
        catch (NotFoundException e) {
            LOG.warn(e);
            request.setAttribute(ERROR_ATTRIBUTE_NAME, format("Задача с id %d не существует!", taskId));
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
