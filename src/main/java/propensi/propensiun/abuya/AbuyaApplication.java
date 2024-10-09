package propensi.propensiun.abuya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("propensi.propensiun.abuya.repository")
public class AbuyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbuyaApplication.class, args);
	}

}
