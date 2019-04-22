package com.test.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class PullRequestDTO {

    private String branchName;
    private String repoName;
    private String title;
}
