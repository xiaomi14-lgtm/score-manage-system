# scoreqa —— 班级学生成绩管理 + 知识库问答系统

基于 **JavaEE 经典三层架构（Servlet + JSP + JSTL + JDBC）** 的课程作业项目，
使用 **外部独立 Tomcat 9.x** 作为运行容器（Spring Boot 2.7 仅用于依赖管理与自动装配），
`javax.servlet` 命名空间。

---

## ⚡ 最快运行方式（推荐，无需安装 Tomcat）

> 前置条件：**JDK 17** + **MySQL 8.x 已启动**（root 密码默认 `root`，如不同请改
> `src/main/resources/application.yaml` 与 `db.properties`）。

项目已内置内嵌 Tomcat，直接一条命令即可启动：

```bash
# Windows（双击 run.bat 或执行）
run.bat
# 等价于： mvnw.cmd spring-boot:run

# macOS / Linux
./run.sh
# 等价于： ./mvnw spring-boot:run
```

启动后浏览器访问：<http://localhost:8080/> ，用下方默认账号登录即可。
数据库 `scoreqa` 与所有表、演示数据会在首次启动时**自动创建**，无需手动建库。

> 也可以在 IntelliJ IDEA 中直接运行 `com.demo.scoreqa.ScoreqaApplication` 的 `main` 方法，
> 效果相同（使用内嵌 Tomcat，端口 8080）。

---


## 一、技术栈

| 层次 | 技术 |
|------|------|
| 表现层 | JSP + JSTL + 原生 Servlet |
| 控制层 | Servlet（`web.xml` 注册） |
| 业务层 | Service（`com.demo.scoreqa.service`） |
| 持久层 | DAO + JDBC（`com.demo.scoreqa.dao`） |
| 数据库 | MySQL 8.x |
| 运行容器 | **外部独立 Tomcat 9.x**（war 部署；Spring Boot 2.7.18 提供依赖管理） |

---

## 二、环境要求

- **JDK 17**（`pom.xml` 中 `java.version=17`）
- **Maven 3.6+**（或使用项目自带的 `mvnw` / `mvnw.cmd`）
- **MySQL 8.x**（本地默认 `localhost:3306`）
- **Apache Tomcat 9.x**（必须是 9.x，因为项目使用 `javax.servlet`；Tomcat 10+ 为 `jakarta.servlet`，不兼容）

---

## 三、数据库配置

### 1. 启动 MySQL 并确认账号

默认配置（见 `src/main/resources/application.yaml` 与 `db.properties`）：

```
地址：localhost:3306
数据库：scoreqa（首次启动自动创建，createDatabaseIfNotExist=true）
用户名：root
密码：root
```

> 如果你的 MySQL root 密码不是 `root`，请**同时修改**以下两个文件中的密码：
> - `src/main/resources/application.yaml` → `spring.datasource.password`
> - `src/main/resources/db.properties` → `jdbc.password`

### 2. 表结构与初始数据

- `src/main/resources/schema.sql`：建表脚本（`sys_user` / `sys_class` / `sys_student` / `sys_course` / `sys_score` / `sys_knowledge`）
- `src/main/resources/data.sql`：演示数据（幂等，使用 `INSERT IGNORE`，可重复执行）

应用启动时由 Spring 的 `spring.sql.init` 自动执行上述脚本，**无需手动建库建表**。

---

## 四、运行方式（外部 Tomcat 部署）

### 1. 打包 war

```bash
# Windows
mvnw.cmd clean package -DskipTests

# macOS / Linux
./mvnw clean package -DskipTests
```

产物：`target/scoreqa.war`（标准 war，内嵌 Tomcat 已排除，不会与外部 Tomcat 冲突）。

### 2. 部署到 Tomcat

把 `target/scoreqa.war` 复制到 Tomcat 的 `webapps/` 目录，然后启动 Tomcat：

```bash
# Windows
%CATALINA_HOME%\bin\startup.bat

# macOS / Linux
$CATALINA_HOME/bin/startup.sh
```

访问地址：<http://localhost:8080/scoreqa/>

> 说明：war 名为 `scoreqa.war`，因此 context path 为 `/scoreqa`。
> 若想直接以根路径访问，把 war 改名为 `ROOT.war` 后部署即可。

### 3. IntelliJ IDEA 中配置 Tomcat（推荐开发时使用）

1. `Run` → `Edit Configurations` → `+` → `Tomcat Server` → `Local`；
2. `Application server` 选择本机 Tomcat 9.x 安装目录；
3. `Deployment` 页签 → `+` → `Artifact` → 选择 `scoreqa:war exploded`；
4. `Application context` 填 `/scoreqa`（或 `/`）；
5. 启动后访问 <http://localhost:8080/scoreqa/>。

### 4. 本地调试（可选，内嵌 Tomcat）

`spring-boot-starter-tomcat` 为 `provided` 作用域，IDEA 中直接运行
`com.demo.scoreqa.ScoreqaApplication` 的 `main` 方法仍可启动内嵌 Tomcat 调试，
访问 <http://localhost:8080/>。

---

## 五、默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | `admin` | `admin123` |
| 教师 | `teacher1` | `123456` |
| 学生 | `student1` | `123456` |

> 说明：`data.sql` 中的密码哈希为占位值，应用启动后由
> `com.demo.scoreqa.config.DataInitializer` 自动将上述演示账号的密码
> 重置为文档约定的明文密码，确保一定能登录。

---

## 六、功能模块

- **用户管理**（管理员）：增删改查、重置密码
- **班级管理**：班级信息、班主任、学生人数统计
- **学生管理**：学号、姓名、班级、性别
- **课程管理**：课程名称、学分、授课教师
- **成绩管理**：录入/编辑/删除成绩，自动判定等级（优秀/良好/中等/及格/不及格）
- **我的成绩**（学生端）：查看本人各科成绩
- **知识库问答**：问答录入、关键字检索

---

## 七、目录结构

```
src/main/java/com/demo/scoreqa/
├── ScoreqaApplication.java      # 启动类
├── config/DataInitializer.java  # 启动时初始化演示账号密码
├── entity/                      # 实体（User/Clazz/Student/Course/Score/Knowledge）
├── dao/                         # JDBC 数据访问层
├── service/                     # 业务层
├── servlet/                     # 控制器（Login/Logout/User/Class/Student/Course/Score/Knowledge）
├── filter/                      # EncodingFilter（UTF-8）、AuthFilter（登录拦截）
└── util/                        # DBUtil（JDBC 连接）、PasswordUtil（PBKDF2 密码哈希）

src/main/resources/
├── application.yaml             # Spring Boot 配置
├── db.properties                # JDBC 连接配置（DBUtil 使用）
├── schema.sql                   # 建表脚本
└── data.sql                     # 演示数据

src/main/webapp/
├── login.jsp / index.jsp        # 登录页 / 首页
├── css/style.css                # 全局样式
└── WEB-INF/
    ├── web.xml                  # Servlet / Filter 注册
    └── jsp/                     # 各模块视图（user/clazz/student/course/score/kb）
```

---

## 八、常见问题

1. **启动报 `Access denied for user 'root'@'localhost' (using password: YES)`**
   → 说明本机 MySQL 的 root 密码不是默认的 `root`。三种解决方式（任选其一）：
   - **方式一（推荐，无需改文件）**：设置环境变量后启动，例如 PowerShell：
     ```powershell
     $env:DB_PASSWORD="你的真实密码"; run.bat
     ```
     （`DB_USERNAME` / `DB_URL` 同理可覆盖用户名与连接串）
   - **方式二**：修改 `src/main/resources/application.yaml` 的 `spring.datasource.password`
     与 `src/main/resources/db.properties` 的 `jdbc.password` 为真实密码。
   - **方式三**：把 MySQL root 密码改回 `root`：
     ```sql
     ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
     FLUSH PRIVILEGES;
     ```
   启动日志中会打印 `[DBUtil] 数据库连接: url=..., username=..., password=******`，
   可据此确认实际使用的账号。


2. **页面 404 或 JSP 无法渲染**
   → 确认部署的是 `target/scoreqa.war`（而非 `original-scoreqa.war`），并注意访问路径带 context path（`/scoreqa/`）。

3. **启动报 `ClassNotFoundException: javax.servlet.*` 或 `jakarta.servlet.*`**
   → 说明 Tomcat 版本不对：本项目使用 `javax.servlet`，必须用 **Tomcat 9.x**，不能用 Tomcat 10/11。

4. **中文乱码**
   → 已通过 `EncodingFilter` 与 `server.servlet.encoding` 统一为 UTF-8；数据库连接串已带 `characterEncoding=utf8`。

5. **登录提示用户名或密码错误**
   → 确认使用上表默认账号；首次启动后 `DataInitializer` 会自动初始化密码。
