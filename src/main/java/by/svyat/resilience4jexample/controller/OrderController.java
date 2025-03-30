package by.svyat.resilience4jexample.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final RestTemplate restTemplate;

    @GetMapping("/{id}")
    @CircuitBreaker(name = "breaker_instance", fallbackMethod = "fallbackToDb")
    public ResponseEntity<String> getOrderById(@PathVariable("id") int id) {
        ResponseEntity<String> response = restTemplate.getForEntity("/response/200", String.class);
        log.info(response.getBody());
        return ResponseEntity.ok(String.format("Successfully find order with id: [%s]",id));
    }

    @GetMapping("/retry/{id}")
    @Retry(name = "retry_instance")
    public ResponseEntity<String> getOrderByIdRetry(@PathVariable("id") int id) {
        log.debug("Call external service");
        ResponseEntity<String> response = restTemplate.getForEntity("/response/200", String.class);
        log.info(response.getBody());
        return ResponseEntity.ok(String.format("Successfully find order with id: [%s]",id));
    }

    public ResponseEntity<String> fallbackToDb(int id, Exception e) {
        return ResponseEntity.ok(String.format("Successfully find order from db with id %s", id));
    }
}
