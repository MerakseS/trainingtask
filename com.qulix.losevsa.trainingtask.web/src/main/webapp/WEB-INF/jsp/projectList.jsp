<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="projectList" scope="request" type="java.util.List<com.qulix.losevsa.trainingtask.web.entity.Project>"/>
<jsp:useBean id="htmlUtils" class="com.qulix.losevsa.trainingtask.web.utils.HtmlUtils"/>

<html>
<head>
    <title>Проекты</title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <script type="javascript" src="../../js/index.js"></script>
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
                    <button onclick="location.href='project/edit?id=${project.id}'">Изменить</button>
                </td>
                <td class="thButton">
                    <button onclick="location.href='project/delete?id=${project.id}'">Удалить</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <button onclick="location.href='project/new'">Добавить проект</button>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
