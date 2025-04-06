package by.svyat.resilience4jexample;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Resilience4jExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jExampleApplication.class, args);
    }

}
