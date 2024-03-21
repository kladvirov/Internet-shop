package by.kladvirov.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Private order entity")
public class OrderCreationDto {

    @NotNull(message = "User is mandatory for creating order")
    @Positive
    @Schema(description = "The id of user who ordered good", example = "1")
    private Long userId;

    @NotNull(message = "Goods are mandatory for creating order")
    @Positive
    @ArraySchema(schema = @Schema(description = "Ordered goods' ids", example = "[1,2,5]"))
    private List<Long> goodIds;

}
