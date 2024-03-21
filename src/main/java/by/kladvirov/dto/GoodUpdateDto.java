package by.kladvirov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Update good entity")
public class GoodUpdateDto {

    @NotNull(message = "Price can't be null")
    @Range(message = "Price has to be positive one")
    @Schema(description = "Good price", example = "1.58")
    private BigDecimal price;

    @NotNull
    @Schema(description = "Good availability")
    private Boolean isAvailable;

}
