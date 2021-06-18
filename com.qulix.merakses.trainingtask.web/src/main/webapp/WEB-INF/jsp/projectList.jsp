<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="projectList" scope="request" type="java.util.List<entity.Project>"/>

<html>
<head>
    <title>Проекты</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<div>
    <h1>Список проектов</h1>
    <table>
        <c:forEach var="project" items="${projectList}">
            <tr>
                <td><c:out value="${project.name}"/></td>
                <td><c:out value="${project.description}" /></td>
                <td><button onclick="location.href='project/edit?id=${project.id}'">Изменить</button></td>
                <td><button onclick="location.href='project/delete?id=${project.id}'">Удалить</button></td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <button onclick="location.href='project/new'">Добавить проект</button>
</div>
</body>
</html>
