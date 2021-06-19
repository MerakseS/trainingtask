package controller.command.impl.taskCommand;

import entity.enums.Status;
import service.ServiceProvider;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class InsertTaskCommand implements controller.command.Command {
    private static final String TASK_LIST_PATH = "/task";

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

        String name = request.getParameter(NAME_PARAMETER);
        Long projectId = Long.valueOf(request.getParameter(PROJECT_ID_PARAMETER));
        String workTime = request.getParameter(WORK_TIME_PARAMETER);
        String startDate = request.getParameter(START_DATE_PARAMETER);
        String endDate = request.getParameter(END_DATE_PARAMETER);
        String status = request.getParameter(STATUS_PARAMETER);
        Long employeeId = Long.valueOf(request.getParameter(EMPLOYEE_ID_PARAMETER));

        taskService.createTask(name, projectId, workTime, startDate, endDate, status, employeeId);

        response.sendRedirect(TASK_LIST_PATH);
    }
}
