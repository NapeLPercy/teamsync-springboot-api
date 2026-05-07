package com.example.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.example.backend.dao.ProjectRepository;
import com.example.backend.model.AuthenticatedUser;
import com.example.backend.model.Project;
import com.example.backend.service.ProjectService;

import java.util.*;

@RequestMapping("api/projects")
@RestController
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> create(Authentication authentication, @RequestBody Project project) {

        AuthenticatedUser loggedUser = (AuthenticatedUser) authentication.getPrincipal();// get data from context
        String projectId = projectService.createProject(project, loggedUser.getUserId());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "Project successfully added",
                "success", true,
                "data", projectId));
    }

    @GetMapping("/admin/get")
    public ResponseEntity<?> findAll(Authentication authentication) {
        AuthenticatedUser loggedUser = (AuthenticatedUser) authentication.getPrincipal();// get data from context
        List<Project> projects = projectService.viewAllCompanyProjects(loggedUser.getUserId(), loggedUser.getRole());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "success", true,
                "data", projects,
                "message", "Successfully fetched company projects"));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(Authentication authentication, @PathVariable("id") String projectId) {
        AuthenticatedUser loggedUser = (AuthenticatedUser) authentication.getPrincipal();

        String deletedId = projectService.deleteProject(loggedUser.getUserId(), loggedUser.getRole(), projectId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "success", true,
                "data", deletedId,
                "message", "Successfully deleted company projects"));
    }

    /*
     * 
     * 
     * @GetMapping("/{id}")
     * public Optional<Project> findById(@PathVariable String id){
     * return projectRepository.findById(id);
     * }
     * 
     * 
     * 
     */
}
