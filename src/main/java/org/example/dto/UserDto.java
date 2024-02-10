package org.example.dto;

import lombok.*;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private String name;

    private String surname;

    private LocalDate birthDate;

    private String login;
}
