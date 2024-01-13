package com.petproject.guitarschool.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "Введите имя пользователя")
    private String username;
    @NotEmpty(message = "Введите почту")
    private String email;
    @NotEmpty(message = "Введите пароль")
    private String password;

    @NotEmpty(message = "Введите имя")
    private String firstName;
    @NotEmpty(message = "Введите фамилию")
    private String secondName;
    private String thirdName;
    @NotNull(message = "Введите возраст")
    @Min(value = 0,message ="Возраст >0" )
    private Integer age;
}
