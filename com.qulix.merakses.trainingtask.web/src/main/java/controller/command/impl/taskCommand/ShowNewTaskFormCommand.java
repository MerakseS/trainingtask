package controller.command.impl.taskCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowNewTaskFormCommand implements controller.command.Command {
    private static final String TASK_EDIT_PATH = "/WEB-INF/jsp/taskEdit.jsp";

    private static final String TITLE_VALUE = "Добавить задачу";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", TITLE_VALUE);
        request.getRequestDispatcher(TASK_EDIT_PATH).forward(request, response);
    }
}
