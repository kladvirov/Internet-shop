package by.kladvirov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodDto {

    private Long id;

    @NotEmpty(message = "Name can't be null")
    @Size(min = 2, max = 30, message = "Name has to be between 2 and 30 ")
    private String name;

    @NotNull(message = "Price can't be null")
    @Range(message = "Price has to be positive one")
    private BigDecimal price;

    @NotNull
    private Boolean isAvailable;

}
