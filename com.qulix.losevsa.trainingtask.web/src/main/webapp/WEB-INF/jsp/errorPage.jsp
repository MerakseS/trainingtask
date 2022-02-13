<%@ page import="static java.nio.charset.StandardCharsets.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error/Exception Information</title>

    <style>
        p {
            margin-left: 2em;
            text-align: justify;
            width: 50%;
        }
    </style>
</head>
<body>
<div>
    <c:if test="${exception == null && statusCode == null}">
        <h2>Error information is missing</h2>
        <p>Please return to the <a href="${request.getRequestURL()}">Home Page</a>.</p>
    </c:if>

    <h1>Error information</h1>

    <c:if test="${statusCode != null}">
        <h3>Status code</h3>
        <p>${statusCode}</p>
    </c:if>

    <c:if test="${exception != null}">
        <h3>Servlet name</h3>
        <p>${servletName}</p>

        <h3>Exception Type</h3>
        <p>${exception.getClass().getName()}</p>

        <h3>The request URI</h3>
        <p>${requestUri}</p>

        <jsp:useBean id="exception" type="java.lang.Exception" scope="request"/>
        <h3>The exception message</h3>
        <p><%= new String(exception.getMessage().getBytes(), UTF_8)%></p>
    </c:if>
</div>
</body>
</html>
