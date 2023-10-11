<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="Style/style.css">
  <title>Landing Page</title>
</head>
<body>
<div class="home">
  <div class="land">
    <div class="one">
      <h1>Easy Bank</h1>
    </div>
    <p>Discover a world of convenience with our innovative and user-friendly platform. Whether you're managing your clients and employees, we're here to make your experience seamless and enjoyable. With EasyBank, you can expert guidance, and a range of features designed to simplify your life. Join us today and embark on a journey of effortless interaction and endless possibilities.</p>
    <div style="display: block">
      <a href="WEB-INF/Views/employees.jsp" class="button">Employees</a>
      <a href="<%= request.getContextPath()%>/clients" class="button">Clients</a>
    </div>

  </div>
  <img src="./Style/img.jpg" />
</div>
</body>
</html>