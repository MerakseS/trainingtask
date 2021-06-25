<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="service.ProjectService" %>
<%@ page import="entity.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ServiceProvider" %>
<%@ page import="service.EmployeeService" %>
<%@ page import="entity.Employee" %>
<%@ page import="entity.enums.Status" %>

<c:set var="title" scope="page">
    <c:if test="${task != null}">
        Изменить задачу
    </c:if>
    <c:if test="${task == null}">
        Добавить задачу
    </c:if>
</c:set>

<%
    ServiceProvider provider = ServiceProvider.getInstance();

    ProjectService projectService = provider.getProjectService();
    List<Project> projectList = projectService.getAllProjects();
    request.setAttribute("projectList", projectList);

    EmployeeService employeeService = provider.getEmployeeService();
    List<Employee> employeeList = employeeService.getAllEmployees();
    request.setAttribute("employeeList", employeeList);
%>

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
    <c:if test="${task != null}">
    <form action="update" method="POST">
        </c:if>
        <c:if test="${task == null}">
        <form action="insert" method="POST">
            </c:if>
            <c:if test="${task != null}">
                <input type="hidden" name="taskId" value="${task.id}"/>
            </c:if>
            <label>Наименование*<br/>
                <input type="text" name="name" placeholder="Наименование" value="${task.name}"/>
            </label> <br/><br/>
            <label>Проект*<br/>
                <select name="projectId">
                    <c:if test="${selectedProject != null}">
                        <option value="${selectedProject.id}">${selectedProject.name}</option>
                    </c:if>

                    <c:if test="${selectedProject == null}">
                        <c:forEach var="project" items="${projectList}">
                            <option value="${project.id}">${project.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </label> <br/><br/>
            <label>Работа (часы)<br/>
                <input type="text" name="workTime" placeholder="Работа" value="${task.workTime}"/>
            </label> <br/><br/>
            <label>Дата начала<br/>
                <input type="date" name="startDate" placeholder="Дата начала" value="${task.startDate}"/>
            </label> <br/><br/>
            <label>Дата окончания<br/>
                <input type="date" name="endDate" placeholder="Дата окончания" value="${task.endDate}"/>
            </label> <br/><br/>
            <label>Статус*<br/>
                <select name="status">
                    <c:forEach var="status" items="${Status.values()}">
                        <option value="${status}">${status.toString()}</option>
                    </c:forEach>
                </select>
            </label> <br/><br/>
            <label>Сотрудник<br/>
                <select name="employeeId">
                    <c:forEach var="employee" items="${employeeList}">
                        <option value="${employee.id}">
                                ${employee.firstName} ${employee.surName} ${employee.patronymic}
                        </option>
                    </c:forEach>
                </select>
            </label> <br/>
            <p>* – обязательные поля.</p>
            <input type="submit" value="${title}">
            <input type="button" onclick="history.back()" value="Отмена">
        </form>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
