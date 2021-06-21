<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" scope="page">
    <c:if test="${employee != null}">
        Изменить проект
    </c:if>
    <c:if test="${employee == null}">
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
                <br/><input type="text" name="name" placeholder="Наименование" value="${project.name}"/>
            </label> <br/><br/>
            <label>Описание
                <br/><textarea name="description" rows="5" placeholder="Описание">${project.description}</textarea>
            </label> <br/>
            <p>* – обязательные поля.</p>

            <c:if test="${project != null}">
                <table>
                    <c:forEach var="task" items="${project.taskList}">
                        <tr>
                            <td><c:out value="${task.status}"/></td>
                            <td><c:out value="${task.name}"/></td>
                            <td><c:out value="${task.workTime}"/></td>
                            <td><c:out value="${task.startDate}"/></td>
                            <td><c:out value="${task.endDate}"/></td>
                            <td><c:out value="${task.employee.firstName} ${task.employee.surName} ${task.employee.patronymic}"/></td>
                            <td><button onclick="location.href='/task/edit?id=${task.id}&projectId=${project.id}'">Изменить</button></td>
                            <td><button onclick="location.href='/task/delete?id=${task.id}'">Удалить</button></td>
                        </tr>
                    </c:forEach>
                </table>
                <br/>
                <button onclick="location.href='/task/new?projectId=${project.id}'">Добавить задачу</button>
                <br/><br/>
            </c:if>

            <input type="submit" value="${title}">
            <input type="button" onclick="history.back()" value="Отмена">
        </form>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
