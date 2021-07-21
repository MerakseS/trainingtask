package controller.command.impl.taskCommand;

import service.ServiceException;
import service.ServiceProvider;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InsertTaskCommand implements controller.command.Command {
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
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(NEW_TASK_FORM_PATH).forward(request, response);
        }
    }
}
