-- scoreqa schema
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    real_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    email VARCHAR(100),
    phone VARCHAR(20),
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_role(role)
) ENGINE=InnoDB COMMENT='系统用户';
CREATE TABLE IF NOT EXISTS sys_class (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    grade VARCHAR(20),
    teacher_id BIGINT,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_class_grade(grade)
) ENGINE=InnoDB COMMENT='班级';
CREATE TABLE IF NOT EXISTS sys_student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    student_no VARCHAR(30) NOT NULL UNIQUE,
    real_name VARCHAR(50) NOT NULL,
    class_id BIGINT,
    gender VARCHAR(6),
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_student_class(class_id)
) ENGINE=InnoDB COMMENT='学生';
CREATE TABLE IF NOT EXISTS sys_course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    credit DECIMAL(3,1) DEFAULT 2.0,
    teacher_id BIGINT,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_course_name(name)
) ENGINE=InnoDB COMMENT='课程';
CREATE TABLE IF NOT EXISTS sys_score (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    score DECIMAL(5,2),
    exam_time DATETIME,
    remark VARCHAR(200),
    INDEX idx_score_student(student_id),
    INDEX idx_score_course(course_id)
) ENGINE=InnoDB COMMENT='成绩';
CREATE TABLE IF NOT EXISTS sys_knowledge (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(500) NOT NULL,
    answer TEXT NOT NULL,
    category VARCHAR(50),
    create_by VARCHAR(50),
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_knowledge_category(category)
) ENGINE=InnoDB COMMENT='知识库问答';
