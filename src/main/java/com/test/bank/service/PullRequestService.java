package com.test.bank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.test.bank.dto.MergeRequestDTO;
import com.test.bank.dto.PullRequestDTO;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.payload.CreatePullRequestPayload;
import com.test.bank.proxy.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PullRequestService {

    private final GitHubService gitHubService;
    private final TestCaseService testCaseService;

    public Object createPullRequest(CreatePullRequestPayload createPullRequestPayload) {
        Long testCaseId = createPullRequestPayload.getTestCaseId();
        TestCase testCase = testCaseService.getTestCaseById(testCaseId).get();
        Project project = testCase.getProject();

        String fileName = testCase.getTitle().toLowerCase().replace(" ", "-");
        String branchName = String.format("test-case-%s-%s",testCase.getId(), UUID.randomUUID());

        createPullRequestPayload.setBranch(branchName);
        createPullRequestPayload.setRepoName(project.getRepoName());
        createPullRequestPayload.setPath(fileName);
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

    public Object mergePullRequest(MergeRequestDTO mergeRequestDTO) {
        return gitHubService.mergePullRequest(mergeRequestDTO);
    }
}
