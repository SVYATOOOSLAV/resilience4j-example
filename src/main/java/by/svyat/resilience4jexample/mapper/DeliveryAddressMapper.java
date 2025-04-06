package by.svyat.resilience4jexample.mapper;

import by.svyat.resilience4jexample.common.Address;
import by.svyat.resilience4jexample.common.Customer;
import by.svyat.resilience4jexample.common.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        imports = {LocalDateTime.class}
)
public interface DeliveryAddressMapper {

    @Mapping(source = "customer.id", target = "messageHead.id")
    @Mapping(source = "customer.firstName", target = "messageBody.firstName")
    @Mapping(source = "customer.lastName", target = "messageBody.lastName")
    @Mapping(source = "address.street", target = "messageBody.street")
    @Mapping(source = "address.postalCode", target = "messageBody.postalCode")
    @Mapping(target = "messageHead.createdAt", expression = "java(LocalDateTime.now())")
    DeliveryAddress from(Customer customer, Address address);
}
