package controller.command.impl;

import controller.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command if no command was found for command name
 */
public class NotFoundCommand implements Command {
    private static final Logger log = Logger.getLogger(NotFoundCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.warn("Incorrect URI - " + request.getRequestURI());
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
