package com.test.bank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.test.bank.dto.PullRequestDTO;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.payload.CreatePullRequestPayload;
import com.test.bank.proxy.GitHubService;
import com.test.bank.response.TestCaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PullRequestService {

    private final GitHubService gitHubService;
    private final TestCaseService testCaseService;

    public Object createPullRequest(CreatePullRequestPayload createPullRequestPayload) {
        Long testCaseId = createPullRequestPayload.getTestCaseId();
        TestCase testCase = testCaseService.getTestCaseById(testCaseId).get();
        Project project = testCase.getProject();

        String branchAndFileName = testCase.getTitle().toLowerCase().replace(" ", "-");

        createPullRequestPayload.setBranch(branchAndFileName);
        createPullRequestPayload.setRepoName(project.getRepoName());
        createPullRequestPayload.setPath(branchAndFileName);
        createPullRequestPayload.setContent(toYaml(testCase));

        gitHubService.createTestCase(createPullRequestPayload);

        PullRequestDTO pullRequestDTO = PullRequestDTO.of(createPullRequestPayload.getBranch(),
                createPullRequestPayload.getRepoName(), createPullRequestPayload.getMessage());

        return gitHubService.createPullRequest(pullRequestDTO);
    }

    private String toYaml(TestCase testCase) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(testCase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(bytes);
    }
}
