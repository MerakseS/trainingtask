<%@ page import="static java.nio.charset.StandardCharsets.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="htmlUtils" class="com.qulix.losevsa.trainingtask.web.utils.HtmlUtils"/>

<c:set var="title" scope="page">
    <c:if test="${project != null}">
        Изменить проект
    </c:if>
    <c:if test="${project == null}">
        Добавить проект
    </c:if>
</c:set>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <br/><input type="text" name="name" placeholder="Наименование"
                            value="${htmlUtils.escapeHtml(param.name != null ? param.name : project.name)}"/>
            </label> <br/><br/>
            <label>Описание
                <br/><textarea name="description" rows="5"
                               placeholder="Описание">${htmlUtils.escapeHtml(param.description != null ? param.description : project.description)}</textarea>
            </label> <br/>
            <p>* – обязательные поля.</p>
            <input type="submit" value="${title}">
            <a href="<c:url value="/project"/>">
                <input type="button" value="Отмена"/>
            </a>
        </form>
</div>
<div class="container">
    <p><%= new String(errorMessage.getBytes(), UTF_8)%>
    </p>
</div>

<div class="container">
    <c:if test="${project != null}">
        <h3>Список задач проекта</h3>
        <table>
            <thead>
            <tr>
                <th>Статус</th>
                <th>Наименование</th>
                <th>Работа</th>
                <th>Дата начала</th>
                <th>Дата окончания</th>
                <th>Исполнитель</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="task" items="${project.taskList}">
                <jsp:useBean id="task" type="com.qulix.losevsa.trainingtask.web.entity.Task"/>
                <tr>
                    <td>
                        <%= new String(task.getStatus().getName().getBytes(), UTF_8)%>
                    </td>
                    <td>${htmlUtils.escapeHtml(task.name)}</td>
                    <td>${task.workTime == null ? "" : task.workTime}</td>
                    <td>${task.startDate}</td>
                    <td>${task.endDate}</td>
                    <td>
                            ${htmlUtils.escapeHtml(task.employee.firstName)}
                            ${htmlUtils.escapeHtml(task.employee.surName)}
                            ${htmlUtils.escapeHtml(task.employee.patronymic)}
                    </td>
                    <td>
                        <a href="<c:url value="/task/edit?taskId=${task.id}&selectedProjectId=${project.id}"/>">
                            <button>Изменить</button>
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="/task/delete?taskId=${task.id}"/>">
                            <button>Удалить</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br/>
        <a href="<c:url value="/task/new?selectedProjectId=${project.id}"/>">
            <button>Добавить задачу</button>
        </a>
        <br/><br/>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
