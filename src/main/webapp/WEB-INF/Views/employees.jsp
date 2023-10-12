<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employees</title>
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
<div class="left" ><a href="<%= request.getContextPath()%>/createEmployee" class="button">Add employee</a></div>
<c:if test="${param.success == 'true'}">
    <div class="success-message"><p>Employee added successfully!<p></p></div>
</c:if>
<c:if test="${param.success == 'false'}">
    <div class="fail-message"><p>Something went wrong!</p></div>
</c:if>
<c:if test="${param.deleted == 'true'}">
    <div class="success-message"><p>Client deleted successfully!<p></p></div>
</c:if>
<c:if test="${param.deleted == 'false'}">
    <div class="fail-message"><p>Something went wrong!</p></div>
</c:if>
<c:if test="${param.updateSuccess == 'true'}">
    <div class="success-message"><p>Employee updated successfully!<p></p></div>
</c:if>
<c:if test="${param.updateSuccess == 'false'}">
    <div class="fail-message"><p>Something went wrong!</p></div>
</c:if>
<div class="table-div">
    <table>
        <tr>
            <th>Number</th>
            <th>Name</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${employees}" var="employee">
            <tr>
                <td>${employee.getNumber()}</td>
                <td>${employee.getFirstName()} ${employee.getLastName()}</td>
                <td>${employee.getPhone()}</td>
                <td>${employee.getAddress()}</td>
                <td>${employee.getEmail()}</td>
                <td style="display: block">
                    <a href="<%= request.getContextPath()%>/updateEmployee?id=${employee.getNumber()}" class="button2">Update</a>
                    <a href="<%= request.getContextPath()%>/deleteEmployee?id=${employee.getNumber()}" class="button2">Delete</a>
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
