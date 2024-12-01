package propensi.propensiun.abuya.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class AppConfig {

    // Menambahkan bean RestTemplate
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();  // Membuat instance RestTemplate
    }
}
