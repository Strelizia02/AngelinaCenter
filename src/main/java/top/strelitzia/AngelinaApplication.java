package top.strelitzia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = {"top.strelitzia.dao"})
@EnableSwagger2
@EnableScheduling
@EnableAsync
@EnableRabbit
public class AngelinaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AngelinaApplication.class, args);
    }
}
