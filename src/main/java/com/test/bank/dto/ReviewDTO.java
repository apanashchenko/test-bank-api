package com.test.bank.dto;

import io.swagger.client.model.FileDTO;
import io.swagger.client.model.PullRequestResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class ReviewDTO {

    private Long testCaseId;
    private FileDTO file;
    private PullRequestResponse pullRequest;
}
