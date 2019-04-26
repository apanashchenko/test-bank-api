package com.test.bank.service;

import com.test.bank.enums.TestCaseStatus;
import com.test.bank.mapper.TestCaseMapper;
import com.test.bank.model.Review;
import com.test.bank.model.TestCase;
import com.test.bank.payload.CreateTestCasePayload;
import com.test.bank.repository.ReviewRepository;
import com.test.bank.repository.TestCaseRepository;
import io.swagger.client.api.FileControllerApi;
import io.swagger.client.api.PullRequestsControllerApi;
import io.swagger.client.model.FileResponse;
import io.swagger.client.model.PullRequestMergeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.test.bank.utils.YamlUtils.fromYaml;


@Service
@RequiredArgsConstructor
@Transactional
public class PullRequestsService {

    private final PullRequestsControllerApi githubPullRequest;
    private final ReviewRepository reviewRepository;
    private final TestCaseRepository testCaseRepository;
    private final FileControllerApi fileService;


    public PullRequestMergeResponse mergePullRequest(Long mergeRequestId) {
        Review review = reviewRepository.findById(mergeRequestId).get();
        TestCase testCase = review.getTestCase();

        String repoName = testCase.getProject().getRepoName();

        PullRequestMergeResponse mergeResponse = githubPullRequest.mergePullRequestUsingPOST(mergeRequestId, repoName);

        if (mergeResponse.getState().equals(PullRequestMergeResponse.StateEnum.SUCCESS)) {
            if (testCase.getStatus().equals(TestCaseStatus.ON_UPDATED)) {
                FileResponse testCaseFile =
                        fileService.getSingleFileUsingGET(testCase.getId() + ".yml", testCase.getProject().getRepoName());
                String content = testCaseFile.getContent();
                CreateTestCasePayload payload = fromYaml(content);

//              TODO maybe need change update test case method
                testCase.setTitle(payload.getTitle());
                testCase.setReference(payload.getReference());
                testCase.setLabels(payload.getLabels());
                testCase.setChangedBy(payload.getChangedBy());
                testCase.removeReview(review.getId());
            }

            testCase.setStatus(TestCaseStatus.READY);
            testCase.removeReview(review.getId());

            testCaseRepository.save(testCase);
        }

        return mergeResponse;
    }
}
