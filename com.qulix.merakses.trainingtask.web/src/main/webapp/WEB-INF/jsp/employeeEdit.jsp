<%@ page import="entity.Employee" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="title" scope="request" type="java.lang.String"/>

<%
    Employee employee = (Employee) pageContext.getAttribute("bean", PageContext.REQUEST_SCOPE);
%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${title}</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div>
    <h1>${title}</h1>
    <c:if test="${employee != null}">
        <form action="update?id=${employee.id}" method="POST">
    </c:if>
    <c:if test="${employee == null}">
        <form action="insert" method="POST">
    </c:if>
            <label>Имя*
                <br/><input type="text" name="firstname" placeholder="Имя" value="${employee.firstName}"/>
            </label> <br/><br/>
            <label>Фамилия*
                <br/><input type="text" name="surname" placeholder="Фамилия" value="${employee.surName}"/>
            </label> <br/><br/>
            <label>Отчество
                <br/><input type="text" name="patronymic" placeholder="Отчество" value="${employee.patronymic}"/>
            </label> <br/><br/>
            <label>Должность*
                <br/><input type="text" name="position" placeholder="Должность" value="${employee.position}"/>
            </label> <br/><br/>
            <input type="submit" value="${title}"> <br/><br/>
            <input type="button" onclick="history.back()" value="Отмена">
            <p>* – обязательные поля.</p>
        </form>
</div>
</body>
</html>
