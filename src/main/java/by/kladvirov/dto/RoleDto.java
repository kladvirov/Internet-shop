package by.kladvirov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Role entity")
@EqualsAndHashCode
public class RoleDto {

    @Schema(description = "Identifier")
    private Long id;

    @NotEmpty(message = "Name can't be null")
    @Schema(description = "Role name", example = "USER")
    private String name;

}
