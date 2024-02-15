package org.example.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private Boolean isAvailable;
}
