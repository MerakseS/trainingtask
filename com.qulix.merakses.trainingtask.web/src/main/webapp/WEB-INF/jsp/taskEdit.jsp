<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="service.ProjectService" %>
<%@ page import="entity.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ServiceProvider" %>
<%@ page import="service.EmployeeService" %>
<%@ page import="entity.Employee" %>
<%@ page import="entity.enums.Status" %>
<%@ page import="static java.nio.charset.StandardCharsets.UTF_8" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>

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
                <input type="text" name="name" placeholder="Наименование"
                       value="${param.name != null ? param.name : task.name}"/>
            </label> <br/><br/>

            <label>Проект*<br/>
                <select name="projectId">
                    <c:if test="${selectedProject != null}">
                        <option value="${selectedProject.id}">${selectedProject.name}</option>
                    </c:if>

                    <c:if test="${selectedProject == null}">
                        <c:forEach var="project" items="${projectList}">
                            <option value="${project.id}"
                                    <c:if test="${param.projectId != null
                                    && param.projectId.equals(Long.toString(project.id))}">
                                        selected
                                    </c:if>
                            >${project.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </label> <br/><br/>

            <label>Работа (часы)<br/>
                <input type="text" name="workTime" placeholder="Работа"
                       value="${param.workTime != null ? param.workTime :
                       (task.workTime == null ? "" : task.workTime)}"/>
            </label> <br/><br/>

            <label>Дата начала<br/>
                <input type="date" name="startDate" placeholder="Дата начала"
                       value="${param.startDate != null ? param.startDate : task.startDate}"/>
            </label> <br/><br/>

            <label>Дата окончания<br/>
                <input type="date" name="endDate" placeholder="Дата окончания"
                       value="${param.endDate != null ? param.endDate : task.endDate}"/>
            </label> <br/><br/>

            <label>Статус*<br/>
                <select name="status">
                    <c:forEach var="status" items="${Status.values()}">
                        <option value="${status}"
                                <c:if test="${param.status != null
                                && status == Status.valueOf(param.status)}">
                                    selected
                                </c:if>
                        >${status.toString()}</option>
                    </c:forEach>
                </select>
            </label> <br/><br/>

            <label>Исполнитель<br/>
                <select name="employeeId">
                    <option value=" "></option>
                    <c:forEach var="employee" items="${employeeList}">
                        <option value="${employee.id}"
                                <c:if test="${param.employeeId != null
                                && param.employeeId.equals(Long.toString(employee.id))}">
                                    selected
                                </c:if>
                        >
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
<div class="container">
    <p><%= new String(errorMessage.getBytes(), UTF_8)%>
    </p>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
