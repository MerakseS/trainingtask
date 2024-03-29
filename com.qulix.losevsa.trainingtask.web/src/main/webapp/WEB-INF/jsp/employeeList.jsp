<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="employeeList" scope="request"
             type="java.util.List<com.qulix.losevsa.trainingtask.web.entity.Employee>"/>
<jsp:useBean id="htmlUtils" class="com.qulix.losevsa.trainingtask.web.utils.HtmlUtils"/>

<html>
<head>
    <title>Сотрудники</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Список сотрудников"/>
</jsp:include>

<div class="container">
    <table>
        <thead>
        <tr>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Должность</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="employee" items="${employeeList}">
            <jsp:useBean id="employee" class="com.qulix.losevsa.trainingtask.web.entity.Employee"/>
            <tr>
                <td>${htmlUtils.escapeHtml(employee.surname)}</td>
                <td>${htmlUtils.escapeHtml(employee.firstName)}</td>
                <td>${htmlUtils.escapeHtml(employee.patronymic)}</td>
                <td>${htmlUtils.escapeHtml(employee.position)}</td>
                <td class="thButton">
                    <a href="employee/edit?id=${employee.id}">
                        <button>Изменить</button>
                    </a>
                </td>
                <td class="thButton">
                    <a href="employee/delete?id=${employee.id}">
                        <button>Удалить</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <a href="employee/new">
        <button>Добавить сотрудника</button>
    </a>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
