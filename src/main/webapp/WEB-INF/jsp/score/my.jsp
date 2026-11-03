<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>我的成绩 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar">
    <div class="brand">我的成绩 <span>scoreqa</span></div>
    <div class="topbar-right"><a class="btn-logout" href="${pageContext.request.contextPath}/logoutServlet">注销</a></div>
  </header>
  <div class="body">
    <aside class="sidebar">
      <nav>
        <a href="${pageContext.request.contextPath}/index.jsp">首页</a>
        <a href="${pageContext.request.contextPath}/scoreServlet?action=my">我的成绩</a>
        <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
      </nav>
    </aside>
    <main class="content">
      <div class="toolbar"><h2>我的成绩</h2></div>
      <table class="table">
        <thead>
        <tr><th>ID</th><th>课程</th><th>分数</th><th>等级</th><th>备注</th></tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${scores}">
          <tr>
            <td>${s.id}</td>
            <td><c:out value="${s.courseName}"/></td>
            <td>${s.score}</td>
            <td><c:out value="${s.level}"/></td>
            <td><c:out value="${s.remark}"/></td>
          </tr>
        </c:forEach>
        <c:if test="${empty scores}"><tr><td colspan="5" class="empty">暂无成绩数据</td></tr></c:if>
        </tbody>
      </table>
    </main>
  </div>
</div>
</body>
</html>
