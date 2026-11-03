-- 演示数据（幂等：重复执行不会报错）
-- 注意：密码哈希为占位值，应用启动后由 DataInitializer 重置为文档约定的明文密码：
--   admin / admin123
--   teacher1 / 123456
--   student1 / 123456
INSERT IGNORE INTO sys_user (username,password,real_name,role,status,email,phone,create_time) VALUES
('admin','10000:AQIDBAUGBwgJCgsMDQ4PEA==:xvkXCtGR/8VgarLWFb7kPNqVRfFrm+yTV7uZbDDeRKo=','系统管理员','ADMIN',1,'admin@scoreqa.com','13800000000',NOW()),
('teacher1','10000:AQIDBAUGBwgJCgsMDQ4PEA==:xvkXCtGR/8VgarLWFb7kPNqVRfFrm+yTV7uZbDDeRKo=','张老师','TEACHER',1,'teacher@scoreqa.com','13800000001',NOW()),
('student1','10000:AQIDBAUGBwgJCgsMDQ4PEA==:xvkXCtGR/8VgarLWFb7kPNqVRfFrm+yTV7uZbDDeRKo=','李学生','STUDENT',1,'student@scoreqa.com','13800000002',NOW());

INSERT IGNORE INTO sys_class (id,name,grade,teacher_id,create_time) VALUES (1,'计算机一班','2024级',2,NOW());
INSERT IGNORE INTO sys_student (id,user_id,student_no,real_name,class_id,gender,create_time) VALUES (1,3,'S001','李学生',1,'男',NOW());
INSERT IGNORE INTO sys_course (id,name,credit,teacher_id,create_time) VALUES (1,'数据库原理',2.0,2,NOW());
INSERT IGNORE INTO sys_score (id,student_id,course_id,score,exam_time,remark) VALUES (1,1,1,88.5,NOW(),'良好');

INSERT IGNORE INTO sys_knowledge (id,question,answer,category,create_by,create_time) VALUES
(1,'如何录入学生成绩？','进入“成绩管理”页面，点击“录入成绩”，选择学生和课程后填写分数并保存。','成绩管理','系统管理员',NOW()),
(2,'如何新增班级？','进入“班级管理”页面，点击“新增班级”，填写班级名称、年级并选择班主任后保存。','班级管理','系统管理员',NOW());
