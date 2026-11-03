<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>编辑用户 - scoreqa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="layout">
  <header class="topbar">
    <div class="brand">编辑用户 <span>scoreqa</span></div>
    <div class="topbar-right"><a class="btn-logout" href="${pageContext.request.contextPath}/logoutServlet">注销</a></div>
  </header>
  <div class="body">
    <main class="content content-center">
      <div class="panel">
        <h2>编辑用户</h2>
        <form action="${pageContext.request.contextPath}/userServlet" method="post">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="id" value="${user.id}">
          <div class="form-item"><label>用户名</label><input type="text" value="${user.username}" readonly disabled class="readonly"></div>
          <div class="form-item"><label>姓名</label><input type="text" name="realName" value="${user.realName}" required></div>
          <div class="form-item">
            <label>角色</label>
            <select name="role">
              <option value="TEACHER" ${user.role=='TEACHER'?'selected':''}>教师</option>
              <option value="STUDENT" ${user.role=='STUDENT'?'selected':''}>学生</option>
              <option value="ADMIN" ${user.role=='ADMIN'?'selected':''}>管理员</option>
            </select>
          </div>
          <div class="form-item"><label>邮箱</label><input type="email" name="email" value="${user.email}"></div>
          <div class="form-item"><label>电话</label><input type="text" name="phone" value="${user.phone}"></div>
          <div class="form-item">
            <label>状态</label>
            <select name="status">
              <option value="1" ${user.status==1?'selected':''}>启用</option>
              <option value="0" ${user.status==0?'selected':''}>禁用</option>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn-primary">保存</button>
            <a class="btn" href="${pageContext.request.contextPath}/userServlet">返回</a>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>
</body>
</html>
