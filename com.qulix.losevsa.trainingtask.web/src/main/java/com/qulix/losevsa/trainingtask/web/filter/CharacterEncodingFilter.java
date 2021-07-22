package com.qulix.losevsa.trainingtask.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * The type Character encoding com.qulix.losevsa.trainingtask.web.filter using to set encoding to UTF-8.
 */
public class CharacterEncodingFilter implements Filter {

    private static final String charsetEncoding = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(charsetEncoding);
        response.setCharacterEncoding(charsetEncoding);
        chain.doFilter(request, response);
    }
}
