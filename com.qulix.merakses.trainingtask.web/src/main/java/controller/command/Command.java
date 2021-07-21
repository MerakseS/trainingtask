package controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The interface Command.
 */
public interface Command {

    /**
     * Execute.
     *
     * @param request  the {@link HttpServletRequest} object that
     * contains the request the client made of the servlet
     *
     * @param response the {@link HttpServletResponse} object that
     * contains the response the servlet returns to the client
     *
     * @exception IOException if an input or output error occurs
     * while the servlet is handling the HTTP request
     *
     * @exception ServletException if the HTTP request
     * cannot be handled
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
