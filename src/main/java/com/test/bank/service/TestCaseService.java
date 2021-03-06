package com.test.bank.service;

import com.test.bank.enums.TestCaseStatus;
import com.test.bank.mapper.TestCaseMapper;
import com.test.bank.model.Project;
import com.test.bank.model.Review;
import com.test.bank.model.TestCase;
import com.test.bank.payload.CreateTestCasePayload;
import com.test.bank.payload.UpdateTestCasePayload;
import com.test.bank.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;

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

    public Review updateTestCase(Long testCaseId, UpdateTestCasePayload payload) {
        TestCase testCase = testCaseRepository.findById(testCaseId).get();
        testCase.setStatus(TestCaseStatus.ON_UPDATED);
        testCaseRepository.save(testCase);

        String projectName = testCase.getProject().getRepoName();

        UpdateFilePayload updateFilePayload = new UpdateFilePayload()
                .fileName(testCaseId + ".yml")
                .message("Update file " + testCaseId)
                .content(YamlUtils.toYaml(testCaseMapper.toTestCaseDTO(payload)))
                .committer(payload.getCommitter());

        FileDTO fileDTO = fileService.updateFileUsingPUT(projectName, updateFilePayload);

        PullRequestDTO pullRequestDTO = new PullRequestDTO()
                .branchName(fileDTO.getBranch())
                .title("Review for " + fileDTO.getFileName());

        PullRequestResponse pullRequest = pullRequestService.createPullRequestUsingPOST(pullRequestDTO, projectName);

        Review review = createReview(testCase, fileDTO, pullRequest);
        testCase.addReview(review);

        reviewRepository.save(review);

        return review;
    }

    public Optional<TestCase> getTestCaseById(Long id) {
        return testCaseRepository.findById(id);
    }

    public Review promoteToReview(Long id, Committer committer) {
        TestCase testCase = testCaseRepository.findById(id).get();
        String projectName = testCase.getProject().getRepoName();

        CreateFilePayload createFilePayload = new CreateFilePayload()
                .fileName(id + ".yml")
                .message("Create file " + id)
                .content(YamlUtils.toYaml(testCaseMapper.toTestCaseDTO(testCase)))
                .committer(committer);

        FileDTO fileDTO = fileService.createFileUsingPOST(createFilePayload, projectName);

        PullRequestDTO pullRequestDTO = new PullRequestDTO()
                .branchName(fileDTO.getBranch())
                .title("Review for " + fileDTO.getFileName());

        PullRequestResponse pullRequest = pullRequestService.createPullRequestUsingPOST(pullRequestDTO, projectName);

        testCase.setStatus(TestCaseStatus.ON_REVIEW);

        testCaseRepository.save(testCase);

        Review review = createReview(testCase, fileDTO, pullRequest);

        testCase.addReview(review);

        reviewRepository.save(review);

        return review;
    }

    private Review createReview(TestCase testCase, FileDTO fileDTO, PullRequestResponse pullRequest) {
        Review review = new Review();
        review.setTestCase(testCase);
        review.setBranch(fileDTO.getBranch());
        review.setPath(fileDTO.getPath());
        review.setDiff(pullRequest.getDiffText());
        review.setPullRequestId(pullRequest.getId());

        return review;
    }

}
