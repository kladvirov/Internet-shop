package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDto {

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String login;

    private String password;

    private List<Long> roleIds;

}
