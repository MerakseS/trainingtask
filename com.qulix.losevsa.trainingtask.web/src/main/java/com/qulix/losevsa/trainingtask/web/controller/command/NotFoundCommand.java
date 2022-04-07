package com.qulix.losevsa.trainingtask.web.controller.command;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Command if no command was found for command name
 */
public class NotFoundCommand implements Command {

    private static final Logger LOG = Logger.getLogger(NotFoundCommand.class);

    private static final String NOT_FOUND_PAGE_PATH = "/WEB-INF/jsp/notFoundPage.jsp";
    private static final String MESSAGE_ATTRIBUTE_NAME = "message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.warn(format("Incorrect URI - %s", request.getRequestURI()));
        request.setAttribute(MESSAGE_ATTRIBUTE_NAME, "Данной страницы не существует. :(");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        request.getRequestDispatcher(NOT_FOUND_PAGE_PATH).forward(request, response);
    }
}
