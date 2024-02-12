package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationDto {

    private User user;

    private String status;
}
