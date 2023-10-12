<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Clients</title>
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
<div class="left" >
    <div class="wrap" style="padding-left: 12%">
        <form action="<%= request.getContextPath()%>/clients" method="get" class="search">
            <input type="text" name="code" id="code" placeholder="Enter client code!">
            <button type="submit" class="button">
                Search
            </button>
        </form>
    </div>
    <a href="<%= request.getContextPath()%>/createClient" class="button">Add client</a>
</div>
<c:if test="${param.success == 'true'}">
    <div class="success-message"><p>Client added successfully!<p></p></div>
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
    <div class="success-message"><p>Client updated successfully!<p></p></div>
</c:if>
<c:if test="${param.updateSuccess == 'false'}">
    <div class="fail-message"><p>Something went wrong!</p></div>
</c:if>
<div class="table-div">
    <table>
        <tr>
            <th>Code</th>
            <th>Name</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Actions</th>
        </tr>
        <c:choose>
            <c:when test="${not empty client}">
                <tr>
                    <td>${client.getCode()}</td>
                    <td>${client.getFirstName()} ${client.getLastName()}</td>
                    <td>${client.getPhone()}</td>
                    <td>${client.getAddress()}</td>
                    <td style="display: block">
                        <a href="<%= request.getContextPath()%>/updateClient?id=${client.getCode()}" class="button2">Update</a>
                        <a href="<%= request.getContextPath()%>/deleteClient?id=${client.getCode()}" class="button2">Delete</a>
                    </td>
                </tr>
            </c:when>

            <c:otherwise>
                <c:forEach items="${clients}" var="client">
                    <tr>
                        <td>${client.getCode()}</td>
                        <td>${client.getFirstName()} ${client.getLastName()}</td>
                        <td>${client.getPhone()}</td>
                        <td>${client.getAddress()}</td>
                        <td style="display: block">
                            <a href="<%= request.getContextPath()%>/updateClient?id=${client.getCode()}" class="button2">Update</a>
                            <a href="<%= request.getContextPath()%>/deleteClient?id=${client.getCode()}" class="button2">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </table>
</div>
<footer class="custom-footer">
    &copy; 2023 Easy Bank
</footer>
</body>
</html>
