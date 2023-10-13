<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="Style/style.css">
  <title>Landing Page</title>
</head>
<body>
<header class="custom-header">
  <a style="text-decoration: none"  href="<%= request.getContextPath()%>/index.jsp" class="header-title">Easy Bank</a>
  <div class="buttons">
    <a href="<%= request.getContextPath()%>/clients" class="green-button">Clients</a>
    <a href="<%= request.getContextPath()%>/employees" class="grey-button">Employees</a>
  </div>
</header>
<div class="home">
  <div class="land">
    <div class="one">
      <h1>Easy Bank</h1>
    </div>
    <p>Discover a world of convenience with our innovative and user-friendly platform. Whether you're managing your clients and employees, we're here to make your experience seamless and enjoyable. With EasyBank, you can expert guidance, and a range of features designed to simplify your life. Join us today and embark on a journey of effortless interaction and endless possibilities.</p>
    <div style="display: block">
      <a href="<%= request.getContextPath()%>/employees" class="button">Employees</a>
      <a href="<%= request.getContextPath()%>/clients" class="button">Clients</a>
    </div>

  </div>
  <img src="./Style/img.jpg" />
</div>
<footer class="custom-footer">
  &copy; 2023 Easy Bank
</footer>
</body>
</html>