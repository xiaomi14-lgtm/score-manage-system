<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>编辑成绩 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar"><div class="brand">编辑成绩 <span>scoreqa</span></div></header>
  <div class="body">
    <main class="content content-center">
      <div class="panel">
        <h2>编辑成绩</h2>
        <form action="${pageContext.request.contextPath}/scoreServlet" method="post">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="id" value="${score.id}">
          <div class="form-item">
            <label>学生</label>
            <select name="studentId" required>
              <c:forEach var="s" items="${students}">
                <option value="${s.id}" ${s.id == score.studentId ? 'selected' : ''}><c:out value="${s.studentNo}"/> - <c:out value="${s.realName}"/></option>
              </c:forEach>
            </select>
          </div>
          <div class="form-item">
            <label>课程</label>
            <select name="courseId" required>
              <c:forEach var="c" items="${courses}">
                <option value="${c.id}" ${c.id == score.courseId ? 'selected' : ''}><c:out value="${c.name}"/></option>
              </c:forEach>
            </select>
          </div>
          <div class="form-item"><label>分数</label><input type="number" min="0" max="100" name="score" value="${score.score}" required></div>
          <div class="form-item"><label>备注</label><input type="text" name="remark" value="${score.remark}"></div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <a class="btn" href="${pageContext.request.contextPath}/scoreServlet">返回</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
</body>
</html>
