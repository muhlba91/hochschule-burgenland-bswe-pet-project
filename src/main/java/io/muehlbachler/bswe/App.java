package io.muehlbachler.bswe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main class for the Spring Boot application.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class App {
    /**
     * Main method.
     * 
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Creates a new RestTemplate bean.
     *
     * @return a new RestTemplate bean
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
