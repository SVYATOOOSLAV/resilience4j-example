package by.svyat.resilience4jexample.service;

import by.svyat.resilience4jexample.common.Address;
import by.svyat.resilience4jexample.common.Customer;
import by.svyat.resilience4jexample.common.DeliveryAddress;
import by.svyat.resilience4jexample.mapper.DeliveryAddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final DeliveryAddressMapper mapper;

    public DeliveryAddress getDeliveryAddress(Long customerId) {
        log.debug("Get customer with id {}", customerId);
        // Get customer from db...

        log.debug("Get address with customer id {}", customerId);
        // Get customer address from db...

        DeliveryAddress deliveryAddress =  mapper.from(
                new Customer(customerId, "DUMMY firstname", "DUMMY postal code"),
                new Address("DUMMY street", "DUMMY postalCode")
        );

        log.debug("Successfully getting delivery address by customer id {}", customerId);

        return deliveryAddress;
    }
}
