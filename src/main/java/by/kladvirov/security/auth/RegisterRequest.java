package by.kladvirov.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {

    private String name;

    private String surname;

    private String login;

    private String password;

    private LocalDate birthDate;

}
