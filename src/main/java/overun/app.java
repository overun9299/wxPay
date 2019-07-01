package overun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName: app
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/20 20:06
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class app {

    public static void main(String[] args) {
        SpringApplication.run(app.class , args);
    }
}
