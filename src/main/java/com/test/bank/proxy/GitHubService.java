package com.test.bank.proxy;

import com.test.bank.dto.MergeRequestDTO;
import com.test.bank.dto.PullRequestDTO;
import com.test.bank.payload.CreateProjectPayload;
import com.test.bank.payload.CreatePullRequestPayload;
import com.test.bank.response.GetTestCaseResponse;
import com.test.bank.response.InitRepoResponse;
import com.test.bank.response.PullRequestMergeResponse;
import com.test.bank.response.CreateTestCaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("githubService")
public interface GitHubService {

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    InitRepoResponse initProjectRepo(CreateProjectPayload createProjectPayload);

    @RequestMapping(value = "/case", method = RequestMethod.POST)
    CreateTestCaseResponse createTestCase(CreatePullRequestPayload projectDTO);

    @RequestMapping(value = "/pull-request", method = RequestMethod.POST)
    Object createPullRequest(PullRequestDTO pullRequestDTO);

    @RequestMapping(value = "/pull-request/merge", method = RequestMethod.POST)
    PullRequestMergeResponse mergePullRequest(MergeRequestDTO id);

    @RequestMapping(value = "/{repoName}/case", method = RequestMethod.GET)
    GetTestCaseResponse getTestCase(@RequestParam String repoName, @RequestParam String fileName);
}
