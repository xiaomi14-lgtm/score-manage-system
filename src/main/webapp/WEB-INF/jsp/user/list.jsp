<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>用户管理 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar">
    <div class="brand">用户管理 <span>scoreqa</span></div>
    <div class="topbar-right">
      <span><c:out value="${sessionScope.loginUser.realName}"/></span>
      <a class="btn-logout" href="${pageContext.request.contextPath}/logoutServlet">注销</a>
    </div>
  </header>
  <div class="body">
    <aside class="sidebar">
      <nav>
        <a href="${pageContext.request.contextPath}/index.jsp">首页</a>
        <a href="${pageContext.request.contextPath}/userServlet">用户管理</a>
        <a href="${pageContext.request.contextPath}/classServlet">班级管理</a>
        <a href="${pageContext.request.contextPath}/studentServlet">学生管理</a>
        <a href="${pageContext.request.contextPath}/courseServlet">课程管理</a>
        <a href="${pageContext.request.contextPath}/scoreServlet">成绩管理</a>
        <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
      </nav>
    </aside>
    <main class="content">
      <div class="toolbar">
        <h2>用户列表</h2>
        <a class="btn-primary" href="${pageContext.request.contextPath}/userServlet?action=add">新增用户</a>
      </div>
      <table class="table">
        <thead>
        <tr><th>ID</th><th>用户名</th><th>姓名</th><th>角色</th><th>状态</th><th>邮箱</th><th>操作</th></tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
          <tr>
            <td>${u.id}</td>
            <td><c:out value="${u.username}"/></td>
            <td><c:out value="${u.realName}"/></td>
            <td>
              <c:choose>
                <c:when test="${u.role == 'ADMIN'}">管理员</c:when>
                <c:when test="${u.role == 'TEACHER'}">教师</c:when>
                <c:otherwise>学生</c:otherwise>
              </c:choose>
            </td>
            <td><c:choose><c:when test="${u.status == 1}">启用</c:when><c:otherwise>禁用</c:otherwise></c:choose></td>
            <td><c:out value="${u.email}"/></td>
            <td class="ops">
              <a href="${pageContext.request.contextPath}/userServlet?action=edit&id=${u.id}">编辑</a>
              <a href="${pageContext.request.contextPath}/userServlet?action=delete&id=${u.id}" onclick="return confirm('确定删除该用户？')">删除</a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty users}">
          <tr><td colspan="7" class="empty">暂无数据</td></tr>
        </c:if>
        </tbody>
      </table>
    </main>
  </div>
</div>
</body>
</html>
