package com.example.ecommerce_api.Product.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class ErrorResponse {
    private List<String> messages;
    private LocalDateTime timestamp;

    public ErrorResponse(List<String> messages) {
        this.messages = messages;
        this.timestamp = LocalDateTime.now();
    }
}