package by.kladvirov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Good entity")
public class GoodDto {

    private Long id;

    @NotEmpty(message = "Name can't be null")
    @Size(min = 2, max = 30, message = "Name has to be between 2 and 30 ")
    @Schema(description = "Good name", example = "Pencil")
    private String name;

    @NotNull(message = "Price can't be null")
    @Range(message = "Price has to be positive one")
    @Schema(description = "Good price", example = "1.58")
    private BigDecimal price;

    @NotNull
    @Schema(description = "Good availability")
    private Boolean isAvailable;

}
