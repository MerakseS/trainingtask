<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="employeeList" scope="request" type="java.util.List<entity.Employee>"/>

<html>
<head>
    <title>${title}</title>
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
            <tr>
                <td>${employee.surName}</td>
                <td>${employee.firstName}</td>
                <td>${employee.patronymic}</td>
                <td>${employee.position}</td>
                <td class="thButton">
                    <button onclick="location.href='employee/edit?id=${employee.id}'">Изменить</button>
                </td>
                <td class="thButton">
                    <button onclick="location.href='employee/delete?id=${employee.id}'">Удалить</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <button onclick="location.href='employee/new'">Добавить сотрудника</button>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
