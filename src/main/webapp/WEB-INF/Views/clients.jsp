<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Clients</title>
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
<div class="left" ><button class="button">Add client</button></div>
<div class="table-div">
    <table>
        <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${clients}" var="client">
            <tr>
                <td>${client.getCode()}</td>
                <td>${client.getFirstName()} ${client.getLastName()}</td>
                <td>${client.getPhone()}</td>
                <td>${client.getAddress()}</td>
                <td style="display: block">
                    <button class="button2">Update</button>
                    <button class="button2">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<footer class="custom-footer">
    &copy; 2023 Easy Bank
</footer>
</body>
</html>
