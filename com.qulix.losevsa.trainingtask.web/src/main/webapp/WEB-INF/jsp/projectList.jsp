<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="projectList" scope="request" type="java.util.List<com.qulix.losevsa.trainingtask.web.entity.Project>"/>
<jsp:useBean id="htmlUtils" class="com.qulix.losevsa.trainingtask.web.utils.HtmlUtils"/>

<html>
<head>
    <title>Проекты</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Список проектов"/>
</jsp:include>

<div class="container">
    <table>
        <thead>
        <tr>
            <th>Наименование</th>
            <th>Описание</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="project" items="${projectList}">
            <tr>
                <td>${htmlUtils.escapeHtml(project.name)}</td>
                <td>${htmlUtils.escapeHtml(project.description)}</td>
                <td class="thButton">
                    <a href="project/edit?id=${project.id}">
                        <button>Изменить</button>
                    </a>
                </td>
                <td class="thButton">
                    <a href="project/delete?id=${project.id}">
                        <button>Удалить</button>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <a href="project/new">
        <button>Добавить проект</button>
    </a>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
