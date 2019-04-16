package com.test.bank.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestCaseDTO {

    @ApiModelProperty(example = "User is able to login")
    private String title;
    @ApiModelProperty(example = "Jira-658")
    private String reference;
}
