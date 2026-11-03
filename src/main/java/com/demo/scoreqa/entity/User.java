package com.demo.scoreqa.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户实体（sys_user 表）
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_TEACHER = "TEACHER";
    public static final String ROLE_STUDENT = "STUDENT";

    private Long id;
    private String username;
    private String password;
    private String realName;
    private String role;
    private Integer status;           // 1启用 0禁用
    private String email;
    private String phone;
    private LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public boolean isAdmin() { return ROLE_ADMIN.equals(role); }
    public boolean isTeacher() { return ROLE_TEACHER.equals(role); }
    public boolean isStudent() { return ROLE_STUDENT.equals(role); }
    public boolean isEnabled() { return status != null && status == 1; }
}
