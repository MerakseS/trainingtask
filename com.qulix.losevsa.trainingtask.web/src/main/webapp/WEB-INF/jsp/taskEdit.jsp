<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.service.ProjectService" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.entity.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.service.ServiceProvider" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.service.EmployeeService" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.entity.Employee" %>
<%@ page import="com.qulix.losevsa.trainingtask.web.entity.enums.Status" %>
<%@ page import="static java.nio.charset.StandardCharsets.UTF_8" %>

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
                <select name="status">
                    <c:forEach var="status" items="${Status.values()}">
                        <jsp:useBean id="status" type="com.qulix.losevsa.trainingtask.web.entity.enums.Status"/>
                        <option value="${status}"
                                <c:if test="${param.status != null && status == Status.valueOf(param.status)}">
                                    selected
                                </c:if>

                                <c:if test="${param.status == null && status == task.status}">
                                    selected
                                </c:if>
                        ><%= new String(status.getName().getBytes(), UTF_8)%>
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
                                ${htmlUtils.escapeHtml(employee.surName)}
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

<jsp:include page="footer.jsp"/>

</body>
</html>
