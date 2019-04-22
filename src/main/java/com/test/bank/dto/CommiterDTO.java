package com.test.bank.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommiterDTO {
    @ApiModelProperty(example = "Ivan Petrov")
    private String name;
    @ApiModelProperty(example = "ivan_89@gmail.com")
    private String email;
}
