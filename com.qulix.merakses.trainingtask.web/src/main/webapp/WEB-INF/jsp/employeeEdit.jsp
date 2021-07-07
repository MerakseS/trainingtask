<%@ page import="static java.nio.charset.StandardCharsets.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

<c:set var="title" scope="page">
    <c:if test="${employee != null}">
        Изменить сотрудника
    </c:if>
    <c:if test="${employee == null}">
        Добавить сотрудника
    </c:if>
</c:set>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${title}</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>

<div class="container">
    <c:if test="${employee != null}">
        <form action="update" method="POST">
    </c:if>
    <c:if test="${employee == null}">
        <form action="insert" method="POST">
    </c:if>
            <c:if test="${employee != null}">
                <input type="hidden" name="id" value="${employee.id}"/>
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
            </label> <br/>
            <p>* – обязательные поля.</p>
            <input type="submit" value="${title}">
            <input type="button" onclick="history.back()" value="Отмена">
        </form>
</div>
<div class="container">
    <p><%= new String(errorMessage.getBytes(), UTF_8)%></p>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
