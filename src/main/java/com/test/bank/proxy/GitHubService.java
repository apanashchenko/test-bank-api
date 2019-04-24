package com.test.bank.proxy;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient("githubService")
public interface GitHubService {

//    @RequestMapping(value = "/project", method = RequestMethod.POST)
//    InitRepoResponse initProjectRepo(CreateProjectPayload createProjectPayload);
//
//    @RequestMapping(value = "/case", method = RequestMethod.POST)
//    CreateTestCaseResponse createTestCase(CreatePullRequestPayload projectDTO);
//
//    @RequestMapping(value = "/pull-request", method = RequestMethod.POST)
//    Object createPullRequest(PullRequestDTO pullRequestDTO);
//
//    @RequestMapping(value = "/pull-request/merge", method = RequestMethod.POST)
//    PullRequestMergeResponse mergePullRequest(MergeRequestDTO id);
//
//    @RequestMapping(value = "/{repoName}/case", method = RequestMethod.GET)
//    GetTestCaseResponse getTestCase(@RequestParam String repoName, @RequestParam String fileName);
}
