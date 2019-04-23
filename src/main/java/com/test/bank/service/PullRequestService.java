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
import com.test.bank.repository.TestCaseRepository;
import com.test.bank.response.GetTestCaseResponse;
import com.test.bank.response.PullRequestMergeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PullRequestService {

    private final GitHubService gitHubService;
    private final TestCaseService testCaseService;
    private final TestCaseRepository testCaseRepository;

    @Value("${fileExtension}")
    private String fileExtension;

    private final ObjectMapper ymlMapper = new ObjectMapper(new YAMLFactory());

    public Object createPullRequest(CreatePullRequestPayload createPullRequestPayload) {
        Long testCaseId = createPullRequestPayload.getTestCaseId();
        TestCase testCase = testCaseService.getTestCaseById(testCaseId).get();
        Project project = testCase.getProject();

        String fileName = testCase.getId() + fileExtension;
        String branchName = String.format("test-case-%s-%s", testCase.getId(), UUID.randomUUID());

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
        byte[] bytes;
        try {
            bytes = ymlMapper.writeValueAsBytes(testCase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(bytes);
    }

    public boolean mergePullRequest(MergeRequestDTO mergeRequestDTO) {
        PullRequestMergeResponse response = gitHubService.mergePullRequest(mergeRequestDTO);

        Project project = testCaseRepository.findById(mergeRequestDTO.getId()).get().getProject();

        if ("SUCCESS".equals(response.getState())) {
            GetTestCaseResponse testCaseResponse = gitHubService.getTestCase(mergeRequestDTO.getRepoName(), mergeRequestDTO.getId() + fileExtension);
            TestCase testCase = extractTestCase(testCaseResponse);
            testCase.setReviewRequired(false);
            testCase.setProject(project);

            testCaseRepository.save(testCase);
            return true;
        }

        return false;
    }

    private TestCase extractTestCase(GetTestCaseResponse response) {
        try {
            return ymlMapper.readValue(response.getContent(), TestCase.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
