<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>新增班级 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar"><div class="brand">新增班级 <span>scoreqa</span></div></header>
  <div class="body">
    <main class="content content-center">
      <div class="panel">
        <h2>新增班级</h2>
        <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/classServlet" method="post">
          <input type="hidden" name="action" value="save">
          <div class="form-item"><label>班级名称</label><input type="text" name="name" required></div>
          <div class="form-item"><label>年级</label><input type="text" name="grade"></div>
          <div class="form-item">
            <label>班主任</label>
            <select name="teacherId">
              <option value="">请选择</option>
              <c:forEach var="t" items="${teachers}">
                <option value="${t.id}"><c:out value="${t.realName}"/></option>
              </c:forEach>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <a class="btn" href="${pageContext.request.contextPath}/classServlet">返回</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
</body>
</html>
