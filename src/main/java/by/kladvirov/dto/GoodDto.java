package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
