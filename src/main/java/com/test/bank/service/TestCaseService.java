package com.test.bank.service;

import com.test.bank.dto.ReviewDTO;
import com.test.bank.enums.TestCaseStatus;
import com.test.bank.mapper.TestCaseMapper;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.payload.CreateTestCasePayload;
import com.test.bank.repository.TestCaseRepository;
import com.test.bank.utils.YamlUtils;
import io.swagger.client.api.FileControllerApi;
import io.swagger.client.api.PullRequestsControllerApi;
import io.swagger.client.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ProjectsService projectsService;
    private final TestCaseMapper testCaseMapper;
    private final FileControllerApi fileService;
    private final PullRequestsControllerApi pullRequestService;

    public TestCase addTestCase(Long id, CreateTestCasePayload payload) {
        Project project = projectsService.findProjectById(id).get();
        TestCase testCase = testCaseMapper.toTestCase(payload);

        testCase.setStatus(TestCaseStatus.NEW);
        project.addTestCase(testCase);

        return testCase;
    }

    public List<TestCase> getAllTestCases(Long id) {
        return testCaseRepository.findAllByProjectId(id);
    }

//    public TestCase updateTestCase(TestCaseDTO newTestCaseDTO) {
//        TestCase newTestCase = testCaseMapper.toTestCase(newTestCaseDTO);
//
//        return testCaseRepository.save(newTestCase);
//    }

    public Optional<TestCase> getTestCaseById(Long id) {
        return testCaseRepository.findById(id);
    }

    public ReviewDTO promoteToReview(Long id, Committer committer) {
        TestCase testCase = testCaseRepository.findById(id).get();
        String projectName = testCase.getProject().getRepoName();

        CreateFilePayload createFilePayload = new CreateFilePayload()
                .fileName(id + ".yml")
                .message("Create file " + id)
                .content(YamlUtils.toYaml(testCase))
                .committer(committer);

        FileDTO fileDTO = fileService.createFileUsingPOST(createFilePayload, projectName);

        PullRequestDTO pullRequestDTO = new PullRequestDTO()
                .branchName(fileDTO.getBranch())
                .title("Review for " + fileDTO.getFileName());

        PullRequestResponse pullRequest = pullRequestService.createPullRequestUsingPOST(pullRequestDTO, projectName);

        testCase.setStatus(TestCaseStatus.ON_REVIEW);

        Long testCaseId = testCaseRepository.save(testCase).getId();

        return ReviewDTO.of(testCaseId, fileDTO, pullRequest);
    }
}
