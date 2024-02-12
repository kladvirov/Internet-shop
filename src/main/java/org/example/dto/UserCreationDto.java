package org.example.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserCreationDto {

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String login;

    private String password;

    private Boolean isBlocked;
}
