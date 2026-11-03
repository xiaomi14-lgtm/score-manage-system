package com.demo.scoreqa.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生实体（sys_student 表）
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String studentNo;
    private String realName;
    private Long classId;
    private String className;   // 冗余字段，用于列表展示
    private String gender;
    private LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
