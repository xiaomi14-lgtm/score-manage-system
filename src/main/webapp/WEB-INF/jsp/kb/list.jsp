<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>知识库问答 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar">
    <div class="brand">知识库问答 <span>scoreqa</span></div>
    <div class="topbar-right"><a class="btn-logout" href="${pageContext.request.contextPath}/logoutServlet">注销</a></div>
  </header>
  <div class="body">
    <aside class="sidebar">
      <nav>
        <a href="${pageContext.request.contextPath}/index.jsp">首页</a>
        <a href="${pageContext.request.contextPath}/questionServlet">知识库问答</a>
      </nav>
    </aside>
    <main class="content">
      <div class="toolbar">
        <h2>知识库问答</h2>
        <a class="btn-primary" href="${pageContext.request.contextPath}/questionServlet?action=add">新增问答</a>
      </div>
      <form class="searchbar" action="${pageContext.request.contextPath}/questionServlet" method="get">
        <input type="text" name="keyword" value="${keyword}" placeholder="搜索问题/答案">
        <button type="submit" class="btn">搜索</button>
      </form>
      <table class="table">
        <thead>
        <tr><th>ID</th><th>问题</th><th>答案</th><th>分类</th><th>录入人</th><th>操作</th></tr>
        </thead>
        <tbody>
        <c:forEach var="q" items="${questions}">
          <tr>
            <td>${q.id}</td>
            <td><c:out value="${q.question}"/></td>
            <td><c:out value="${q.answer}"/></td>
            <td><c:out value="${q.category}"/></td>
            <td><c:out value="${q.createBy}"/></td>
            <td class="ops">
              <a href="${pageContext.request.contextPath}/questionServlet?action=edit&id=${q.id}">编辑</a>
              <a href="${pageContext.request.contextPath}/questionServlet?action=delete&id=${q.id}" onclick="return confirm('确定删除该问答？')">删除</a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty questions}"><tr><td colspan="6" class="empty">暂无数据</td></tr></c:if>
        </tbody>
      </table>
    </main>
  </div>
</div>
</body>
</html>
