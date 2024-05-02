package com.hackaboss.PruebaTecnica4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private String dni;
    private String name;
    private String lastName;
    private String email;
}
