package org.example;

import lombok.*;

@Data
@AllArgsConstructor
 public class Sms {
    private String phoneNumber;
    private String message;
}
