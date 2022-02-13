package com.qulix.losevsa.trainingtask.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qulix.losevsa.trainingtask.web.service.NotFoundException;

/**
 * The Exception handler.
 */
public class ExceptionHandler extends HttpServlet {

    private static final String EXCEPTION_NAME = "javax.servlet.error.exception";
    private static final String STATUS_CODE_NAME = "javax.servlet.error.status_code";
    private static final String SERVLET_NAME = "javax.servlet.error.servlet_name";
    private static final String REQUEST_URI_NAME = "javax.servlet.error.request_uri";

    private static final String ERROR_PAGE_PATH = "/WEB-INF/jsp/errorPage.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute(EXCEPTION_NAME);

        Integer statusCode;
        if (exception instanceof NotFoundException) {
            statusCode = 404;
            response.setStatus(statusCode);
        }
        else {
            statusCode = (Integer) request.getAttribute(STATUS_CODE_NAME);
        }

        String servletName = (String) request.getAttribute(SERVLET_NAME);
        if (servletName == null) {
            servletName = "Unknown";
        }

        String requestUri = (String) request.getAttribute(REQUEST_URI_NAME);
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        request.setAttribute("exception", exception);
        request.setAttribute("statusCode", statusCode);
        request.setAttribute("servletName", servletName);
        request.setAttribute("requestUri", requestUri);

        request.getRequestDispatcher(ERROR_PAGE_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doGet(req, resp);
    }
}
