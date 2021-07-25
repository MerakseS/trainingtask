package com.qulix.losevsa.trainingtask.web.controller.command.impl;

import java.io.IOException;

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
        LOG.warn("Incorrect URI - " + request.getRequestURI());
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
