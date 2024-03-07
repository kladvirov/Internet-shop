package by.kladvirov.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityCreationDto {

    @NotEmpty(message = "Name can't be null")
    private String name;

}
