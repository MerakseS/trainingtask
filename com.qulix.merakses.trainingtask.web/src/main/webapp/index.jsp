<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Training task</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="../css/style.css" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>

<jsp:include page="WEB-INF/jsp/header.jsp">
    <jsp:param name="title" value="Меню"/>
</jsp:include>

<div class="container">
    <h2 class="container"><a href="project">Проекты</a></h2><br/>
    <h2 class="container"><a href="task">Задачи</a></h2><br/>
    <h2 class="container"><a href="employee">Работники</a></h2>
</div>

<jsp:include page="WEB-INF/jsp/footer.jsp"/>

</body>
</html>
