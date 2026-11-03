<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>新增学生 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar"><div class="brand">新增学生 <span>scoreqa</span></div></header>
  <div class="body">
    <main class="content content-center">
      <div class="panel">
        <h2>新增学生</h2>
        <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/studentServlet" method="post">
          <input type="hidden" name="action" value="save">
          <div class="form-item"><label>学号</label><input type="text" name="studentNo" required></div>
          <div class="form-item"><label>姓名</label><input type="text" name="realName" required></div>
          <div class="form-item">
            <label>班级</label>
            <select name="classId">
              <option value="">请选择</option>
              <c:forEach var="c" items="${classes}">
                <option value="${c.id}"><c:out value="${c.name}"/></option>
              </c:forEach>
            </select>
          </div>
          <div class="form-item">
            <label>性别</label>
            <select name="gender">
              <option value="男">男</option>
              <option value="女">女</option>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <a class="btn" href="${pageContext.request.contextPath}/studentServlet">返回</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
</body>
</html>
