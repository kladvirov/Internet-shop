package org.example.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GoodDto {

    private String name;

    private BigDecimal price;

    private Boolean isAvailable;
}
