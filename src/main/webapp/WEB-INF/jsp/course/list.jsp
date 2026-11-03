<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>课程管理 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar">
    <div class="brand">课程管理 <span>scoreqa</span></div>
    <div class="topbar-right"><span><c:out value="${sessionScope.loginUser.realName}"/></span><a class="btn-logout" href="${pageContext.request.contextPath}/logoutServlet">注销</a></div>
  </header>
  <div class="body">
    <aside class="sidebar">
      <nav>
        <a href="${pageContext.request.contextPath}/index.jsp">首页</a>
        <a href="${pageContext.request.contextPath}/classServlet">班级管理</a>
        <a href="${pageContext.request.contextPath}/studentServlet">学生管理</a>
        <a href="${pageContext.request.contextPath}/courseServlet">课程管理</a>
        <a href="${pageContext.request.contextPath}/scoreServlet">成绩管理</a>
        <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
      </nav>
    </aside>
    <main class="content">
      <div class="toolbar">
        <h2>课程列表</h2>
        <a class="btn-primary" href="${pageContext.request.contextPath}/courseServlet?action=add">新增课程</a>
      </div>
      <table class="table">
        <thead>
        <tr><th>ID</th><th>课程名称</th><th>学分</th><th>授课教师</th><th>操作</th></tr>
        </thead>
        <tbody>
        <c:forEach var="c" items="${courses}">
          <tr>
            <td>${c.id}</td>
            <td><c:out value="${c.name}"/></td>
            <td>${c.credit}</td>
            <td><c:out value="${c.teacherName}"/></td>
            <td class="ops">
              <a href="${pageContext.request.contextPath}/courseServlet?action=edit&id=${c.id}">编辑</a>
              <a href="${pageContext.request.contextPath}/courseServlet?action=delete&id=${c.id}" onclick="return confirm('确定删除该课程？')">删除</a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty courses}"><tr><td colspan="5" class="empty">暂无数据</td></tr></c:if>
        </tbody>
      </table>
    </main>
  </div>
</div>
</body>
</html>
