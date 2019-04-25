package com.test.bank.service;

import com.test.bank.enums.TestCaseStatus;
import com.test.bank.model.Review;
import com.test.bank.model.TestCase;
import com.test.bank.repository.ReviewRepository;
import com.test.bank.repository.TestCaseRepository;
import io.swagger.client.api.PullRequestsControllerApi;
import io.swagger.client.model.PullRequestMergeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PullRequestsService {

    private final PullRequestsControllerApi githubPullRequest;
    private final ReviewRepository reviewRepository;
    private final TestCaseRepository testCaseRepository;

    public PullRequestMergeResponse mergePullRequest(Long mergeRequestId) {
        Review review = reviewRepository.findById(mergeRequestId).get();
        TestCase testCase = review.getTestCase();

        String repoName = testCase.getProject().getRepoName();

        PullRequestMergeResponse mergeResponse = githubPullRequest.mergePullRequestUsingPOST(mergeRequestId, repoName);

        if (mergeResponse.getState().equals(PullRequestMergeResponse.StateEnum.SUCCESS)) {
            testCase.setStatus(TestCaseStatus.READY);
            testCase.removeReview(review.getId());

            testCaseRepository.save(testCase);
        }

        return mergeResponse;
    }
}
