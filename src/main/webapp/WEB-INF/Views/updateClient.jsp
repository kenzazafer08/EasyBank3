<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add client</title>
    <link rel="stylesheet" type="text/css" href="Style/style.css">
</head>
<body>
<header class="custom-header">
    <h1 class="header-title">Easy Bank</h1>
    <div class="buttons">
        <a href="<%= request.getContextPath()%>/clients" class="green-button">Clients</a>
        <a href="<%= request.getContextPath()%>/employees" class="grey-button">Employees</a>
    </div>
</header>
<div class="form">
    <form action="<%= request.getContextPath()%>/updateClient?id=${client.getCode()}" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${client.getFirstName()}" required>
        <br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="${client.getLastName()}" required>
        <br>
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" value="${client.getPhone()}" required>
        <br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${client.getAddress()}" required>
        <br>
        <input type="submit" value="Create Client">
    </form>
</div>
</body>
</html>
