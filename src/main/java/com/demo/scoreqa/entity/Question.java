package com.demo.scoreqa.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 知识库问答实体（知识库表）。
 */
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String question;      // 问题
    private String answer;        // 答案
    private String category;      // 分类
    private String createBy;      // 录入人
    private LocalDateTime createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
