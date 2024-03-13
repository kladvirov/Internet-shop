package by.kladvirov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Private role entity")
public class RoleCreationDto {

    @NotEmpty(message = "Name can't be null")
    @Schema(description = "Role name", example = "USER")
    private String name;

}
