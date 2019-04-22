package com.test.bank.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GithubTestCaseDto {

    private String message;
    @ApiModelProperty(example = "#1")
    private String branch;
    private CommiterDTO committer;
    @ApiModelProperty(example = "LS0tCnRpdGxlOiAiRGVtbyIKdXNlck5hbWU6ICJTdGV2ZSBJdmFub3YiCg==")
    private String content;
    @ApiModelProperty(example = "test-project")
    private String repoName;
    private String path;

}
