package by.svyat.resilience4jexample.controller;

import by.svyat.resilience4jexample.common.Address;
import by.svyat.resilience4jexample.common.Customer;
import by.svyat.resilience4jexample.common.DeliveryAddress;
import by.svyat.resilience4jexample.mapper.DeliveryAddressMapper;
import by.svyat.resilience4jexample.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final RestTemplate restTemplate;
    private final OrderService orderService;

    @GetMapping("/circuitbreaker/{id}")
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

    @GetMapping("/ratelimiter/{id}")
    @RateLimiter(name = "ratelimiter_instance", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<String> getOrderByIdRateLimiter(@PathVariable("id") int id) {
        log.debug("Call external service");
        ResponseEntity<String> response = restTemplate.getForEntity("/response/200", String.class);
        log.info(response.getBody());
        return ResponseEntity.ok(String.format("Successfully find order with id: [%s]",id));
    }

    @GetMapping("/delivery/{id}")
    public ResponseEntity<DeliveryAddress> getDeliveryAddressByIdCustomer(@PathVariable Long id) {
        MDC.put("threadMDC", UUID.randomUUID().toString());

        return ResponseEntity.ok(orderService.getDeliveryAddress(id));
    }

    public ResponseEntity<String> fallbackToDb(int id, Exception e) {
        return ResponseEntity.ok(String.format("Successfully find order from db with id %s", id));
    }

    public ResponseEntity<String> rateLimiterFallback(int id, Exception e) {
        return new ResponseEntity<>("Does not permit further calls", HttpStatus.TOO_MANY_REQUESTS);
    }
}
