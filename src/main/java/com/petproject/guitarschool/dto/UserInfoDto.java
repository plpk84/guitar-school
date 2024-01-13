package com.petproject.guitarschool.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto {
    private Long id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private Integer age;
}
