package ga.surilaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SurilawApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurilawApplication.class, args);
	}

}
