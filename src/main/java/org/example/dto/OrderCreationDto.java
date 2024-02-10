package org.example.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Setter
@Getter
public class OrderCreationDto {

    private LocalDateTime orderDate;

    private String status;
}
