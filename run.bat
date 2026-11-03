@echo off
REM ============================================================
REM  scoreqa 一键启动脚本（Windows）
REM  使用内嵌 Tomcat 运行（spring-boot:run），无需单独安装 Tomcat。
REM  前置条件：已安装 JDK 17、MySQL 8.x 并已启动，root 密码与
REM            src/main/resources/application.yaml 中一致（默认 root/root）。
REM ============================================================
setlocal

echo [scoreqa] 正在启动（内嵌 Tomcat，端口 8080）...
echo [scoreqa] 启动后访问： http://localhost:8080/
echo.

call mvnw.cmd spring-boot:run

endlocal
