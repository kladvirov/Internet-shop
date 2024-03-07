package by.kladvirov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationDto {

    @NotEmpty
    @Size(min = 2, max = 30, message = "Name has to be between 2 and 30 characters")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 32, message = "Surname has to be between 2 and 32 characters")
    private String surname;

    @NotNull(message = "Birthdate can't be null")
    @PastOrPresent(message = "Enter valid date")
    private LocalDate birthDate;

    @NotEmpty(message = "Login can't be null")
    @Size(min = 2, max = 24, message = "Login has to be between 2 and 24 characters")
    private String login;

    @NotEmpty(message = "Password can't be null")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "At least 8 characters, one uppercase letter, one lowercase " +
            "letter and one number are required")
    private String password;

    @NotNull(message = "Role ids are mandatory for for creating user")
    private List<Long> roleIds;

}
