<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add employee</title>
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
    <form action="<%= request.getContextPath()%>/updateEmployee?id=${employee.getNumber()}" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${employee.getFirstName()}" required>
        <br>
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="${employee.getLastName()}" required>
        <br>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${employee.getEmail()}" required>
        <br>
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" value="${employee.getPhone()}" required>
        <br>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${employee.getAddress()}" required>
        <br>
        <input type="submit" value="Update employee">
    </form>
</div>

</body>
</html>
