package com.test.bank.web;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.model.Review;
import com.test.bank.model.TestCase;
import com.test.bank.payload.CreateTestCasePayload;
import com.test.bank.payload.UpdateTestCasePayload;
import com.test.bank.service.ReviewService;
import com.test.bank.service.TestCaseService;
import io.swagger.client.model.Committer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Collections.singletonMap;

@RestController
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;
    private final ReviewService reviewService;

    @PostMapping("/project/{id}/case")
    public ResponseEntity addTestCase(@PathVariable Long id,
                                      @RequestBody CreateTestCasePayload payload) {
        return new ResponseEntity<>(singletonMap("id", testCaseService.addTestCase(id, payload).getId()), HttpStatus.OK);
    }

    @PostMapping("/case/{id}/create-review")
    public Review promoteToReview(@PathVariable Long id, @RequestBody Committer committer) {
        return testCaseService.promoteToReview(id, committer);
    }

    @GetMapping("/project/{id}/cases")
    public List<TestCase> getAllTestCases(@PathVariable Long id) {
        return testCaseService.getAllTestCases(id);
    }

    @GetMapping("/case/{id}/reviews")
    public Iterable<Review> getAllTestCaseReviews(@PathVariable Long id) {
        return reviewService.getAllReviewsForTestCase(id);
    }

    @PutMapping("/case/{testCaseId}/update")
    public Review updateTestCase(@PathVariable Long testCaseId, @RequestBody UpdateTestCasePayload payload) {
        return testCaseService.updateTestCase(testCaseId, payload);
    }

    @GetMapping("/case/{id}")
    public TestCase getTestCaseById(@PathVariable Long id) {
        return testCaseService.getTestCaseById(id).get();
    }
}
