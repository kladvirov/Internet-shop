package by.kladvirov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Private good entity")
public class GoodCreationDto {

    @NotEmpty(message = "Name can't be null")
    @Size(min = 2, max = 30, message = "Name has to be between 2 and 30 ")
    @Schema(description = "Good name", example = "Pencil")
    private String name;

    @NotNull(message = "Price can't be null")
    @Range(message = "Price has to be positive one")
    @Schema(description = "Good price", example = "1.58")
    private BigDecimal price;

    @NotNull(message = "Creation date can't be null")
    @PastOrPresent
    @Schema(description = "Good creation date", example = "2024-03-10")
    private LocalDate createDate;

    @FutureOrPresent
    @Schema(description = "Good expiration date", example = "2025-04-10")
    private LocalDate expirationDate;

    @NotNull
    @Schema(description = "Good availability")
    private Boolean isAvailable;

}
