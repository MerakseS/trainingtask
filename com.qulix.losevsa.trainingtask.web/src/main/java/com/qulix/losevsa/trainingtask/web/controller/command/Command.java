package com.qulix.losevsa.trainingtask.web.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The interface Command.
 */
public interface Command {

    /**
     * Execute specified command.
     *
     * @param request the {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws ServletException if the HTTP request cannot be handled
     * @throws IOException if an input or output error occurs while the servlet is handling the HTTP request
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
