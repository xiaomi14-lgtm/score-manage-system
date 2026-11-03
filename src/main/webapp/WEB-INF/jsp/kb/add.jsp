<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>新增问答 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar"><div class="brand">新增问答 <span>scoreqa</span></div></header>
  <div class="body">
    <main class="content content-center">
      <div class="panel">
        <h2>新增问答</h2>
        <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/questionServlet" method="post">
          <input type="hidden" name="action" value="save">
          <div class="form-item"><label>问题</label><input type="text" name="question" required></div>
          <div class="form-item"><label>答案</label><textarea name="answer" rows="5" required></textarea></div>
          <div class="form-item"><label>分类</label><input type="text" name="category"></div>
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
