package by.kladvirov.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodUpdateDto {

    @NotNull(message = "Price can't be null")
    @Range(message = "Price has to be positive one")
    private BigDecimal price;

    @NotNull
    private Boolean isAvailable;

}
