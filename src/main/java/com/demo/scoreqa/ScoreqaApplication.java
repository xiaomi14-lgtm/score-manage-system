package com.demo.scoreqa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动类。
 * <p>
 * 继承 {@link SpringBootServletInitializer} 后，本应用既可以：
 * <ul>
 *     <li>打包成 war 部署到外部独立 Tomcat（推荐，Tomcat 9.x，javax 命名空间）；</li>
 *     <li>在 IDE 中直接运行 main 方法（此时使用 provided 的内嵌 Tomcat）。</li>
 * </ul>
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.demo.scoreqa")
public class ScoreqaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ScoreqaApplication.class, args);
    }

    /**
     * 外部 Tomcat 启动时通过 SPI 调用此方法初始化 Spring 上下文。
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ScoreqaApplication.class);
    }
}
