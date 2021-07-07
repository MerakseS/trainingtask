<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="taskList" scope="request" type="java.util.List<entity.Task>"/>

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
            <tr>
                <td>${task.status.toString()}</td>
                <td>${task.name}</td>
                <td>${task.project.name}</td>
                <td>${task.workTime == null ? "" : task.workTime}</td>
                <td>${task.startDate}</td>
                <td>${task.endDate}</td>
                <td>${task.employee.firstName} ${task.employee.surName} ${task.employee.patronymic}</td>
                <td class="thButton">
                    <button onclick="location.href='task/edit?taskId=${task.id}'">Изменить</button>
                </td>
                <td class="thButton">
                    <button onclick="location.href='task/delete?taskId=${task.id}'">Удалить</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <button onclick="location.href='task/new'">Добавить задачу</button>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>

