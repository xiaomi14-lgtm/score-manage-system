package com.demo.scoreqa.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级实体（sys_class 表）。类名用 Clazz 避免与 java.lang.Class 冲突。
 */
public class Clazz implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String grade;
    private Long teacherId;
    private String teacherName;   // 冗余字段，用于列表展示
    private Integer studentCount; // 冗余字段，用于列表展示
    private LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public Integer getStudentCount() { return studentCount; }
    public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
