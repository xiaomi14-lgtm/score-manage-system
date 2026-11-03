<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>scoreqa - 学生成绩管理系统</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar">
    <div class="brand">学生成绩管理 <span>scoreqa</span></div>
    <div class="topbar-right">
      <span>欢迎，<c:out value="${sessionScope.loginUser.realName}"/>（<c:out value="${sessionScope.loginUser.role}"/>）</span>
      <a class="btn-logout" href="${pageContext.request.contextPath}/logoutServlet">注销</a>
    </div>
  </header>

  <div class="body">
    <aside class="sidebar">
      <nav>
        <c:choose>
          <c:when test="${sessionScope.loginUser.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/userServlet">用户管理</a>
            <a href="${pageContext.request.contextPath}/classServlet">班级管理</a>
            <a href="${pageContext.request.contextPath}/studentServlet">学生管理</a>
            <a href="${pageContext.request.contextPath}/courseServlet">课程管理</a>
            <a href="${pageContext.request.contextPath}/scoreServlet">成绩管理</a>
            <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
          </c:when>
          <c:when test="${sessionScope.loginUser.role == 'TEACHER'}">
            <a href="${pageContext.request.contextPath}/classServlet">我的班级</a>
            <a href="${pageContext.request.contextPath}/scoreServlet">成绩登记</a>
            <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
          </c:when>
          <c:otherwise>
            <a href="${pageContext.request.contextPath}/scoreServlet?action=my">我的成绩</a>
            <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
          </c:otherwise>
        </c:choose>
      </nav>
    </aside>

    <main class="content">
      <h2>欢迎使用学生成绩管理系统</h2>
      <p>基于 JavaEE【Servlet + JSP + JSTL + JDBC】经典三层架构实现的课程作业。</p>
      <div class="cards">
        <div class="card"><h3>成绩管理</h3><p>教师录入学生学科成绩，自动判定等级。</p></div>
        <div class="card"><h3>班级/学生管理</h3><p>管理员维护班级、学生、课程信息。</p></div>
        <div class="card"><h3>知识库问答</h3><p>录入常见问题与答案，支持关键字检索。</p></div>
      </div>
    </main>
  </div>
</div>
</body>
</html>
