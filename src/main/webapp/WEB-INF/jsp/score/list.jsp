<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>成绩管理 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar">
    <div class="brand">成绩管理 <span>scoreqa</span></div>
    <div class="topbar-right"><a class="btn-logout" href="${pageContext.request.contextPath}/logoutServlet">注销</a></div>
  </header>
  <div class="body">
    <aside class="sidebar">
      <nav>
        <a href="${pageContext.request.contextPath}/index.jsp">首页</a>
        <a href="${pageContext.request.contextPath}/studentServlet">学生管理</a>
        <a href="${pageContext.request.contextPath}/courseServlet">课程管理</a>
        <a href="${pageContext.request.contextPath}/scoreServlet">成绩管理</a>
        <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
      </nav>
    </aside>
    <main class="content">
      <div class="toolbar">
        <h2>成绩列表</h2>
        <a class="btn-primary" href="${pageContext.request.contextPath}/scoreServlet?action=add">录入成绩</a>
      </div>
      <table class="table">
        <thead>
        <tr><th>ID</th><th>学号</th><th>姓名</th><th>课程</th><th>分数</th><th>等级</th><th>操作</th></tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${scores}">
          <tr>
            <td>${s.id}</td>
            <td><c:out value="${s.studentNo}"/></td>
            <td><c:out value="${s.studentName}"/></td>
            <td><c:out value="${s.courseName}"/></td>
            <td>${s.score}</td>
            <td><span class="level"><c:out value="${s.level}"/></span></td>
            <td class="ops">
              <a href="${pageContext.request.contextPath}/scoreServlet?action=edit&id=${s.id}">编辑</a>
              <a href="${pageContext.request.contextPath}/scoreServlet?action=delete&id=${s.id}" onclick="return confirm('确定删除该成绩？')">删除</a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty scores}"><tr><td colspan="7" class="empty">暂无数据</td></tr></c:if>
        </tbody>
      </table>
    </main>
  </div>
</div>
</body>
</html>
