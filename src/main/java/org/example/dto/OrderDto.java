package org.example.dto;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDto {

    private LocalDateTime orderDate;

    private String status;
}
