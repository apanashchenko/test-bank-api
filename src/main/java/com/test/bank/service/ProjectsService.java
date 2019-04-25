package com.test.bank.service;

import com.test.bank.mapper.ProjectMapper;
import com.test.bank.model.Project;
import com.test.bank.repository.ProjectsRepository;
import io.swagger.client.api.ProjectControllerApi;
import io.swagger.client.model.InitProjectResponse;
import io.swagger.client.model.ProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectsService {

    private final ProjectsRepository projectsRepository;
    private final ProjectMapper projectMapper;
    private final ProjectControllerApi githubService;

    public Long addProject(ProjectDTO projectPayload) {
        System.out.println("About to create project " + projectPayload.getName());

        Project project = projectMapper.toProject(projectPayload);

        InitProjectResponse initRepoResponse = githubService.initProjectUsingPOST(projectPayload);

        project.setRepoName(initRepoResponse.getName());

        Long id = projectsRepository.save(project).getId();

        System.out.println("Project created " + id);
        return id;
    }

    public List<com.test.bank.dto.ProjectDTO> getAllProjects() {
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
