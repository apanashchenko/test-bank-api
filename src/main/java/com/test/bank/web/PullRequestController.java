package com.test.bank.web;

import com.test.bank.dto.MergeRequestDTO;
import com.test.bank.payload.CreatePullRequestPayload;
import com.test.bank.service.PullRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class PullRequestController {

    private final PullRequestService pullRequestService;

    @PostMapping("/pull-request")
    public Object createPullRequest(@RequestBody CreatePullRequestPayload createPullRequestPayload) {
        return pullRequestService.createPullRequest(createPullRequestPayload);
    }

    @PostMapping("/pull-request/merge")
    public Object mergePullRequest(@RequestBody MergeRequestDTO mergeRequestDTO) {
        boolean result = pullRequestService.mergePullRequest(mergeRequestDTO);
        return new ResponseEntity<>(Collections.singletonMap("merged", result), HttpStatus.OK);
    }
}
