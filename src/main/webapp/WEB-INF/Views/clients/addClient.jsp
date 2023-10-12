<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add client</title>
    <link rel="stylesheet" type="text/css" href="Style/style.css">
</head>
<body>
<header class="custom-header">
    <a style="text-decoration: none"  href="/index.jsp" class="header-title">Easy Bank</a>
    <div class="buttons">
        <a href="<%= request.getContextPath()%>/clients" class="green-button">Clients</a>
        <a href="<%= request.getContextPath()%>/employees" class="grey-button">Employees</a>
    </div>
</header>
<div class="form">
    <form class="f" action="<%= request.getContextPath()%>/createClient" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required>
        <br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required>
        <br>
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" required>
        <br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
        <br>
        <input type="submit" value="Create Client">
    </form>
</div>
</body>
</html>
