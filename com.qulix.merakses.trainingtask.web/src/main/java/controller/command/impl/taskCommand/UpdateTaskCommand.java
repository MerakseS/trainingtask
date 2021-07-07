package controller.command.impl.taskCommand;

import service.ServiceException;
import service.ServiceProvider;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateTaskCommand implements controller.command.Command {
    private static final String TASK_LIST_PATH = "/task";
    private static final String EDIT_TASK_FORM_PATH = "/task/edit";

    private static final String ID_PARAMETER = "taskId";
    private static final String NAME_PARAMETER = "name";
    private static final String PROJECT_ID_PARAMETER = "projectId";
    private static final String WORK_TIME_PARAMETER = "workTime";
    private static final String START_DATE_PARAMETER = "startDate";
    private static final String END_DATE_PARAMETER = "endDate";
    private static final String STATUS_PARAMETER = "status";
    private static final String EMPLOYEE_ID_PARAMETER = "employeeId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        TaskService taskService = serviceProvider.getTaskService();

        long taskId = Long.parseLong(request.getParameter(ID_PARAMETER));
        String name = request.getParameter(NAME_PARAMETER);
        String strProjectId = request.getParameter(PROJECT_ID_PARAMETER);
        String workTime = request.getParameter(WORK_TIME_PARAMETER);
        String startDate = request.getParameter(START_DATE_PARAMETER);
        String endDate = request.getParameter(END_DATE_PARAMETER);
        String status = request.getParameter(STATUS_PARAMETER);
        String strEmployeeId = request.getParameter(EMPLOYEE_ID_PARAMETER);

        try {
            taskService.updateTask(taskId, name, strProjectId, workTime, startDate, endDate, status, strEmployeeId);
            response.sendRedirect(TASK_LIST_PATH);
        } catch (ServiceException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(EDIT_TASK_FORM_PATH).forward(request, response);
        }
    }
}
