package by.kladvirov.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Order entity")
public class OrderDto {

    private Long id;

    @NotNull(message = "User is mandatory for creating order")
    @Positive(message = "User's id can't be a negative one")
    @Schema(description = "The id of user who ordered good", example = "1")
    private Long userId;

    @PastOrPresent
    @NotNull(message = "Order date can't be null")
    @Schema(description = "Order date", example = "2024-03-10")
    private LocalDateTime orderDate;

    @NotEmpty(message = "Order status can't be null")
    @Size(min = 2, max = 20, message = "Size has to be between 2 and 20 characters")
    @Schema(description = "Order status", example = "Processing..")
    private String status;

    @NotNull(message = "Goods are mandatory for creating order")
    @Positive(message = "Good ids can't be a negative one")
    @ArraySchema(schema = @Schema(description = "Ordered goods' ids", example = "[1,2,5]"))
    private List<Long> goodIds;

}
