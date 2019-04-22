package com.test.bank.service;

import com.test.bank.payload.CreateProjectPayload;
import com.test.bank.response.InitRepoResponse;
import com.test.bank.dto.ProjectDTO;
import com.test.bank.mapper.ProjectMapper;
import com.test.bank.model.Project;
import com.test.bank.proxy.GitHubService;
import com.test.bank.repository.ProjectsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectsService {

    private final ProjectsRepository projectsRepository;
    private final ProjectMapper projectMapper;
    private final GitHubService gitHubService;

    public Long addProject(CreateProjectPayload projectPayload) {
        System.out.println("About to create project " + projectPayload.getName());

        Project project = projectMapper.toProject(projectPayload);

        InitRepoResponse initRepoResponse = gitHubService.initProjectRepo(projectPayload);

        project.setRepoName(initRepoResponse.getName());

        Long id = projectsRepository.save(project).getId();

        System.out.println("Project created " + id);
        return id;
    }

    public List<ProjectDTO> getAllProjects() {
        Iterable<Project> projects = projectsRepository.findAll();
        return StreamSupport
                .stream(projects.spliterator(), false)
                .map(projectMapper::toProjectDto)
                .collect(Collectors.toList());
    }

    public Optional<Project> findProjectById(Long id) {
        return projectsRepository.findById(id);
    }

    public Optional<Project> getProjectByName(String name) {
        return projectsRepository.findByName(name);
    }

    public boolean deleteProject(Long id) {
        Project project = projectsRepository.findById(id).get();
        projectsRepository.delete(project);
        return true;
    }
}
