package site.zeng.wukongaq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
public class WukongaqApplication {

	public static void main(String[] args) {
		SpringApplication.run(WukongaqApplication.class, args);
	}

}
