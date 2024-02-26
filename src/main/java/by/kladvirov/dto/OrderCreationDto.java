package by.kladvirov.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationDto {

    @NotNull(message = "User is mandatory for creating order")
    @Positive
    private Long userId;

    @NotNull(message = "Goods are mandatory for creating order")
    @Positive
    private List<Long> goodIds;

}
