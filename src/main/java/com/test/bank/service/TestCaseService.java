package com.test.bank.service;

import com.test.bank.dto.GithubTestCaseDto;
import com.test.bank.dto.TestCaseDTO;
import com.test.bank.mapper.TestCaseMapper;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.proxy.GitHubService;
import com.test.bank.repository.TestCaseRepository;
import com.test.bank.response.TestCaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final GitHubService gitHubService;


    public TestCase addTestCase(Long id, TestCaseDTO testCaseDTO) {
        Project project = projectsService.findProjectById(id).get();
        TestCase testCase = testCaseMapper.toTestCase(testCaseDTO);

        GithubTestCaseDto githubTestCaseDto = convertToGithubTestCaseDto(project, testCaseDTO);

        TestCaseResponse testCaseResponse = gitHubService.createTestCase(githubTestCaseDto);

        testCase.setPath(testCaseResponse.getPath());

        project.addTestCase(testCase);

        return testCase;
    }

    public List<TestCase> getAllTestCases(Long id) {
        return testCaseRepository.findAllByProjectId(id);
    }

    public TestCase updateTestCase(Long id, TestCaseDTO newTestCaseDTO) {
        TestCase oldTestCase = testCaseRepository.findById(id).get();

        TestCase newTestCase = testCaseMapper.toTestCase(newTestCaseDTO);

        return testCaseRepository.save(newTestCase);
    }

    public Optional<TestCase> getTestCaseById(Long id) {
        return testCaseRepository.findById(id);
    }

    private GithubTestCaseDto convertToGithubTestCaseDto(Project project, TestCaseDTO testCaseDTO) {
        GithubTestCaseDto githubTestCaseDto = new GithubTestCaseDto();
        githubTestCaseDto.setTitle(testCaseDTO.getTitle());
        githubTestCaseDto.setUserName(testCaseDTO.getChangedBy());
        githubTestCaseDto.setEmail(testCaseDTO.getChangedBy());

        String title = testCaseDTO.getTitle().toLowerCase().replace(" ", "-");

        githubTestCaseDto.setFileName(title);
        githubTestCaseDto.setBranch(title);
        githubTestCaseDto.setRepoName(project.getRepoName());
        return githubTestCaseDto;
    }

}
