<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>登录 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="login-body">
<div class="login-box">
  <h2>学生成绩管理系统</h2>
  <p class="subtitle">scoreqa · JavaEE 三层架构作业</p>

  <c:if test="${not empty error}">
    <div class="alert alert-error"><c:out value="${error}"/></div>
  </c:if>

  <form action="${pageContext.request.contextPath}/loginServlet" method="post">
    <div class="form-item">
      <label>用户名</label>
      <input type="text" name="username" value="${param.username}" required autofocus>
    </div>
    <div class="form-item">
      <label>密码</label>
      <input type="password" name="password" required>
    </div>
    <button type="submit" class="btn-primary btn-block">登 录</button>
  </form>

  <p class="tip">默认账号：admin / admin123（管理员），teacher1 / 123456（教师），student1 / 123456（学生）</p>
</div>
</body>
</html>
