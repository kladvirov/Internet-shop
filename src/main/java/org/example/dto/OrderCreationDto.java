package org.example.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationDto {

    private LocalDateTime orderDate;

    private String status;
}
