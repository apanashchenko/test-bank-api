package com.test.bank.payload;

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
    @ApiModelProperty(hidden = true)
    private String branch;
    private CommiterDTO committer;
    @ApiModelProperty(example = "LS0tCnRpdGxlOiAiRGVtbyIKdXNlck5hbWU6ICJTdGV2ZSBJdmFub3YiCg==", hidden = true)
    private String content;
    @ApiModelProperty(hidden = true)
    private String repoName;
    @ApiModelProperty(hidden = true)
    private String path;
}
