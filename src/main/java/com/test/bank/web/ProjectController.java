package com.test.bank.web;

import com.test.bank.service.ProjectsService;
import io.swagger.client.model.ProjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.test.bank.ControllerKeyConstants.ID_KEY;
import static com.test.bank.ControllerKeyConstants.STATUS_KEY;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectsService projectsService;

    @PostMapping("/project")
    public ResponseEntity createProject(@Valid @RequestBody ProjectDTO project) {
        return new ResponseEntity<>(singletonMap(ID_KEY, projectsService.addProject(project)), OK);
    }

    @GetMapping(value = "/projects")
    public List<com.test.bank.dto.ProjectDTO> getAllProjects() {
        return projectsService.getAllProjects();
    }

    @GetMapping(value = "/project/{id}")
    public ResponseEntity getProjectById(@PathVariable Long id) {
        return projectsService.findProjectById(id)
                .<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap(STATUS_KEY, "No such project with id " + id), NOT_FOUND));
    }

    @GetMapping(value = "/project")
    public ResponseEntity getProjectByName(@RequestParam String name) {
        return projectsService.getProjectByName(name)
                .<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap(STATUS_KEY, "No such project with id or name " + name), NOT_FOUND));
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        projectsService.deleteProject(id);
        return new ResponseEntity<>(singletonMap(STATUS_KEY, "Deleted"), OK);
    }

    @GetMapping("/project/{id}/labels")
    public List<String> getProjectLabels(@PathVariable Long id){
        return Arrays.asList("SMOKE", "REGRESSION","AUTOMATED", "MANUAL");
    }
}


