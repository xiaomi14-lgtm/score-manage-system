<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>新增课程 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar"><div class="brand">新增课程 <span>scoreqa</span></div></header>
  <div class="body">
    <main class="content content-center">
      <div class="panel">
        <h2>新增课程</h2>
        <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/courseServlet" method="post">
          <input type="hidden" name="action" value="save">
          <div class="form-item"><label>课程名称</label><input type="text" name="name" required></div>
          <div class="form-item"><label>学分</label><input type="number" step="0.5" name="credit" value="2.0"></div>
          <div class="form-item">
            <label>授课教师</label>
            <select name="teacherId">
              <option value="">请选择</option>
              <c:forEach var="t" items="${teachers}">
                <option value="${t.id}"><c:out value="${t.realName}"/></option>
              </c:forEach>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <a class="btn" href="${pageContext.request.contextPath}/courseServlet">返回</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
</body>
</html>
