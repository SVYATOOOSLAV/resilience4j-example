package by.svyat.resilience4jexample.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryAddress {
    private MessageHead messageHead;
    private MessageBody messageBody;

    @Data
    public static class MessageHead {
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        private LocalDateTime createdAt;
        private String id;
    }

    @Data
    public static class MessageBody {
        private String firstName;
        private String lastName;
        private String street;
        private String postalCode;
    }
}