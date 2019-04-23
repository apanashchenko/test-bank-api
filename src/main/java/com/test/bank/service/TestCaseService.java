package com.test.bank.service;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.mapper.TestCaseMapper;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.repository.TestCaseRepository;
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

    public TestCase addTestCase(Long id, TestCaseDTO testCaseDTO) {
        Project project = projectsService.findProjectById(id).get();
        TestCase testCase = testCaseMapper.toTestCase(testCaseDTO);

        testCase.setReviewRequired(true);
        project.addTestCase(testCase);

        return testCase;
    }

    public List<TestCase> getAllTestCases(Long id) {
        return testCaseRepository.findAllByProjectId(id);
    }

    public TestCase updateTestCase(TestCaseDTO newTestCaseDTO) {
        TestCase newTestCase = testCaseMapper.toTestCase(newTestCaseDTO);

        return testCaseRepository.save(newTestCase);
    }

    public Optional<TestCase> getTestCaseById(Long id) {
        return testCaseRepository.findById(id);
    }
}
