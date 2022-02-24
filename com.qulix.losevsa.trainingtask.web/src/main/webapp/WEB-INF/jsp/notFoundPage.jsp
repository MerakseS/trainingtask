<%@ page import="static java.nio.charset.StandardCharsets.UTF_8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<html>
<head>
    <title>404</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div class="container">
    <h1>404</h1>
</div>

<div class="container">
    <c:if test="${errorMessage != null}">
        <h3><%= new String(errorMessage.getBytes(), UTF_8)%>
        </h3>
    </c:if>
</div>

<div class="container">
    <a href="<c:url value="/"/>">Вернуться на заглавную страницу.</a>
</div>
</body>
</html>
