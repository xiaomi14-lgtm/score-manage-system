package com.demo.scoreqa.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩实体（sys_score 表）
 */
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long studentId;
    private Long courseId;
    private BigDecimal score;      // 成绩
    private LocalDateTime examTime; // 考试时间
    private String remark;          // 备注

    // 冗余字段，用于列表展示
    private String studentNo;
    private String studentName;
    private String courseName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }

    public LocalDateTime getExamTime() { return examTime; }
    public void setExamTime(LocalDateTime examTime) { this.examTime = examTime; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    /**
     * 根据分数自动判定等级（供 JSP 展示，如 ${s.level}）。
     */
    public String getLevel() {
        if (score == null) {
            return "-";
        }
        double v = score.doubleValue();
        if (v >= 90) return "优秀";
        if (v >= 80) return "良好";
        if (v >= 70) return "中等";
        if (v >= 60) return "及格";
        return "不及格";
    }
}
