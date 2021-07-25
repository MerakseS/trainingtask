<%@ page import="static java.nio.charset.StandardCharsets.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="taskList" scope="request" type="java.util.List<com.qulix.losevsa.trainingtask.web.entity.Task>"/>
<jsp:useBean id="htmlUtils" class="com.qulix.losevsa.trainingtask.web.utils.HtmlUtils"/>

<html>
<head>
    <title>Задачи</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Список задач"/>
</jsp:include>

<div class="container">
    <table>
        <thead>
        <tr>
            <th>Статус</th>
            <th>Наименование</th>
            <th>Наименование проекта</th>
            <th>Работа</th>
            <th>Дата начала</th>
            <th>Дата окончания</th>
            <th>Исполнитель</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="task" items="${taskList}">
            <jsp:useBean id="task" type="com.qulix.losevsa.trainingtask.web.entity.Task"/>
            <tr>
                <td><%= new String(task.getStatus().toString().getBytes(), UTF_8)%></td>
                <td>${htmlUtils.escapeHtml(task.name)}</td>
                <td>${htmlUtils.escapeHtml(task.project.name)}</td>
                <td>${task.workTime == null ? "" : task.workTime}</td>
                <td>${task.startDate}</td>
                <td>${task.endDate}</td>
                <td>
                        ${htmlUtils.escapeHtml(task.employee.firstName)}
                        ${htmlUtils.escapeHtml(task.employee.surName)}
                        ${htmlUtils.escapeHtml(task.employee.patronymic)}
                </td>
                <td class="thButton">
                    <a href="task/edit?taskId=${task.id}">
                        <button>Изменить</button>
                    </a>
                </td>
                <td class="thButton">
                    <a href="task/delete?taskId=${task.id}">
                        <button>Удалить</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <a href="task/new">
        <button>Добавить задачу</button>
    </a>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>

