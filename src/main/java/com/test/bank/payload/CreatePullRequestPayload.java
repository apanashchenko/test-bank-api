package com.test.bank.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.bank.dto.CommiterDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePullRequestPayload {

    private Long testCaseId;
    @ApiModelProperty(example = "#1 added")
    private String message;
    @ApiModelProperty(example = "#1")
    private String branch;
    private CommiterDTO committer;
    @ApiModelProperty(example = "LS0tCnRpdGxlOiAiRGVtbyIKdXNlck5hbWU6ICJTdGV2ZSBJdmFub3YiCg==", hidden = true)
    private String content;
    @ApiModelProperty(example = "test-project")
    private String repoName;
    @ApiModelProperty(example = "cases/1.yml")
    private String path;
}
