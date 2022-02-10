package com.qulix.losevsa.trainingtask.web.controller.command.impl;

import java.io.IOException;
import static java.lang.String.format;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.qulix.losevsa.trainingtask.web.controller.command.Command;

/**
 * Command if no command was found for command name
 */
public class NotFoundCommand implements Command {

    private static final Logger LOG = Logger.getLogger(NotFoundCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.warn(format("Incorrect URI - %s", request.getRequestURI()));
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
