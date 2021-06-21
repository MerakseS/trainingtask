package controller.command.impl.projectCommand;

import controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowNewProjectFormCommand implements Command {
    private static final String PROJECT_EDIT_PATH = "/WEB-INF/jsp/projectEdit.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(PROJECT_EDIT_PATH).forward(request, response);
    }
}
