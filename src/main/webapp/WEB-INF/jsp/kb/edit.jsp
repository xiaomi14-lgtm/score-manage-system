<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>编辑问答 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar"><div class="brand">编辑问答 <span>scoreqa</span></div></header>
  <div class="body">
    <main class="content content-center">
      <div class="panel">
        <h2>编辑问答</h2>
        <form action="${pageContext.request.contextPath}/questionServlet" method="post">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="id" value="${question.id}">
          <div class="form-item"><label>问题</label><input type="text" name="question" value="${question.question}" required></div>
          <div class="form-item"><label>答案</label><textarea name="answer" rows="5" required>${question.answer}</textarea></div>
          <div class="form-item"><label>分类</label><input type="text" name="category" value="${question.category}"></div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <a class="btn" href="${pageContext.request.contextPath}/questionServlet">返回</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
</body>
</html>
