package site.zeng.wukongaq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories
@SpringBootApplication
public class WukongaqApplication {

    public static void main(String[] args) {
        SpringApplication.run(WukongaqApplication.class, args);
    }

}
