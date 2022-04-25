<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.entity.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.entity.Employee" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.entity.TaskStatus" %>
<%@ page import="static java.nio.charset.StandardCharsets.UTF_8" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.service.Service" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.service.DefaultProjectService" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.repository.Repository" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.repository.DefaultEmployeeRepository" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.repository.DefaultProjectRepository" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.entity.Task" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.repository.DefaultTaskRepository" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.service.DefaultEmployeeService" %>

<jsp:useBean id="errorMessage" class="java.lang.String" scope="request"/>
<jsp:useBean id="htmlUtils" class="com.qulix.losevsa.trainingtask.web.utils.HtmlUtils"/>

<c:set var="title" scope="page">
    <c:if test="${task != null}">
        Изменить задачу
    </c:if>
    <c:if test="${task == null}">
        Добавить задачу
    </c:if>
</c:set>

<%
    Repository<Employee> employeeRepository = new DefaultEmployeeRepository();
    Service<Employee> employeeService = new DefaultEmployeeService(employeeRepository);

    Repository<Project> projectRepository = new DefaultProjectRepository();
    Repository<Task> taskRepository = new DefaultTaskRepository(employeeRepository, projectRepository);
    Service<Project> projectService = new DefaultProjectService(projectRepository, taskRepository);

    List<Project> projectList = projectService.getAll();
    request.setAttribute("projectList", projectList);

    List<Employee> employeeList = employeeService.getAll();
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

            <c:if test="${selectedProject != null}">
                <input type="hidden" name="selectedProjectId" value="${selectedProject.id}">
            </c:if>

            <label>Наименование*<br/>
                <input type="text" name="name" placeholder="Наименование"
                       value="${htmlUtils.escapeHtml(param.name != null ? param.name : task.name)}"/>
            </label> <br/><br/>

            <label>Проект*<br/>
                <select name="projectId">
                    <c:if test="${selectedProject != null}">
                        <option value="${selectedProject.id}">${htmlUtils.escapeHtml(selectedProject.name)}</option>
                    </c:if>

                    <c:if test="${selectedProject == null}">
                        <c:forEach var="project" items="${projectList}">
                            <option value="${project.id}"
                                    <c:if test="${param.projectId != null
                                    && param.projectId.equals(Long.toString(project.id))}">
                                        selected
                                    </c:if>

                                    <c:if test="${param.projectId == null && project.id == task.project.id}">
                                        selected
                                    </c:if>
                            >${htmlUtils.escapeHtml(project.name)}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </label> <br/><br/>

            <label>Работа (часы)<br/>
                <input type="text" name="workTime" placeholder="Работа"
                       value="${param.workTime != null ? htmlUtils.escapeHtml(param.workTime) :
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
                <select name="taskStatus">
                    <c:forEach var="taskStatus" items="${TaskStatus.values()}">
                        <jsp:useBean id="taskStatus" type="com.qulix.losevsa.trainingtask.web.entity.TaskStatus"/>
                        <option value="${taskStatus}"
                                <c:if test="${param.taskStatus != null && taskStatus == TaskStatus.valueOf(param.taskStatus)}">
                                    selected
                                </c:if>

                                <c:if test="${param.taskStatus == null && taskStatus == task.taskStatus}">
                                    selected
                                </c:if>
                        ><%= new String(taskStatus.getName().getBytes(), UTF_8)%>
                        </option>
                    </c:forEach>
                </select>
            </label> <br/><br/>

            <label>Исполнитель<br/>
                <select name="employeeId">
                    <option value=" "></option>
                    <c:forEach var="employee" items="${employeeList}">
                        <option value="${employee.id}"
                                <c:if test="${param.employeeId != null &&
                                param.employeeId.equals(Long.toString(employee.id))}">
                                    selected
                                </c:if>

                                <c:if test="${param.employeeId == null && employee.id == task.employee.id}">
                                    selected
                                </c:if>
                        >
                                ${htmlUtils.escapeHtml(employee.firstName)}
                                ${htmlUtils.escapeHtml(employee.surname)}
                                ${htmlUtils.escapeHtml(employee.patronymic)}
                        </option>
                    </c:forEach>
                </select>
            </label> <br/>

            <p>* – обязательные поля.</p>
            <input type="submit" value="${title}"/>
            <a
                    <c:if test="${selectedProject != null}">
                        href="<c:url value="/project/edit?id=${selectedProject.id}"/>"
                    </c:if>
                    <c:if test="${selectedProject == null}">
                        href="<c:url value="/task"/>"
                    </c:if>
            >
                <input type="button" value="Отмена"/>
            </a>
        </form>
</div>
<div class="container">
    <p><%= new String(errorMessage.getBytes(), UTF_8)%>
    </p>
</div>

<script type="text/javascript" src="../../js/index.js"></script>

<jsp:include page="footer.jsp"/>

</body>
</html>
