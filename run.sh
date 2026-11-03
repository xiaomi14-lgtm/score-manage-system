#!/usr/bin/env bash
# ============================================================
#  scoreqa 一键启动脚本（macOS / Linux）
#  使用内嵌 Tomcat 运行（spring-boot:run），无需单独安装 Tomcat。
#  前置条件：已安装 JDK 17、MySQL 8.x 并已启动，root 密码与
#            src/main/resources/application.yaml 中一致（默认 root/root）。
# ============================================================
set -e

echo "[scoreqa] 正在启动（内嵌 Tomcat，端口 8080）..."
echo "[scoreqa] 启动后访问： http://localhost:8080/"
echo

./mvnw spring-boot:run
