package com.test.bank.web;

import com.test.bank.service.PullRequestsService;
import io.swagger.client.model.PullRequestMergeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PullRequestController {

    private final PullRequestsService pullRequestService;

    @PostMapping("/pull-request/{id}/merge")
    public PullRequestMergeResponse mergePullRequest(@PathVariable Long id) {
        return pullRequestService.mergePullRequest(id);
    }
}
