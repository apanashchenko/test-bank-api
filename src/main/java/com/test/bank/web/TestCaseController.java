package com.test.bank.web;

import com.test.bank.model.TestCase;
import com.test.bank.payload.CreateTestCasePayload;
import com.test.bank.service.TestCaseService;
import io.swagger.client.model.Committer;
import io.swagger.client.model.PullRequestResponse;
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

    @PostMapping("/project/{id}/case")
    public ResponseEntity addTestCase(@PathVariable Long id,
                                      @RequestBody CreateTestCasePayload payload) {
        return new ResponseEntity<>(singletonMap("id", testCaseService.addTestCase(id, payload).getId()), HttpStatus.OK);
    }

    @PostMapping("/case/{id}/create-review")
    public PullRequestResponse promoteToReview(@PathVariable Long id, @RequestBody Committer committer) {
        return testCaseService.promoteToReview(id, committer);
    }

    @GetMapping("/project/{id}/cases")
    public List<TestCase> getAllTestCases(@PathVariable Long id) {
        return testCaseService.getAllTestCases(id);
    }
//
//    @PutMapping("/case")
//    public TestCase updateTestCase(@RequestBody TestCaseDTO testCaseDTO) {
//        return testCaseService.updateTestCase(testCaseDTO);
//    }

    @GetMapping("/case/{id}")
    public TestCase getTestCaseById(@PathVariable Long id) {
        return testCaseService.getTestCaseById(id).get();
    }
}
