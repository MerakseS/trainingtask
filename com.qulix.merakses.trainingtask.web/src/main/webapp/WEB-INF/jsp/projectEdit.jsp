<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="title" scope="request" type="java.lang.String"/>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${title}</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div>
    <h1>${title}</h1>
    <c:if test="${project != null}">
        <form action="update" method="POST">
    </c:if>
    <c:if test="${project == null}">
        <form action="insert" method="POST">
    </c:if>
            <c:if test="${project != null}">
                <input type="hidden" name="id" value="${project.id}"/>
            </c:if>
            <label>Наименование*
                <br/><input type="text" name="name" placeholder="Наименование" value="${project.name}"/>
            </label> <br/><br/>
            <label>Описание
                <br/><textarea name="description" rows="5" placeholder="Описание">${project.description}</textarea>
            </label> <br/><br/>
<%--            TODO список задач--%>
            <input type="submit" value="${title}"> <br/><br/>
            <input type="button" onclick="history.back()" value="Отмена">
            <p>* – обязательные поля.</p>
        </form>
</div>
</body>
</html>
