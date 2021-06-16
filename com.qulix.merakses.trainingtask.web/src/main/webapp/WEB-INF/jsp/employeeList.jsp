<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="employeeList" scope="request" type="java.util.List<entity.Employee>"/>

<html>
<head>
    <title>Работники</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div>
    <h1>Список работников</h1>
    <table>
        <c:forEach var="employee" items="${employeeList}">
            <tr>
                <td><c:out value="${employee.firstName} ${employee.surName} ${employee.patronymic}"/></td>
                <td><c:out value="${employee.position}" /></td>
                <td><button onclick="location.href='employee/edit?id=${employee.id}'">Изменить</button></td>
                <td><button onclick="location.href='employee/delete?id=${employee.id}'">Удалить</button></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <button onclick="location.href='employee/new'">Добавить работника</button>
</div>
</body>
</html>
