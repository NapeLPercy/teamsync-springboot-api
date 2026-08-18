package com.example.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dao.ProjectDetailsResponse;
import com.example.backend.dto.ProjectRequest;
import com.example.backend.dto.UserResponse;
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

    @PostMapping("/")
    public ResponseEntity<?> createProject(Authentication authentication, @RequestBody ProjectRequest project) {

        AuthenticatedUser validUser = (AuthenticatedUser) authentication.getPrincipal();// get data from context
        String projectId = projectService.createProject(project, validUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "Project successfully added",
                "success", true,
                "projectId", projectId));
    }

    // get all projectsDetails
    @GetMapping("/details")
    public ResponseEntity<?> findAllProjectsDetails(Authentication authentication) {
        AuthenticatedUser validUser = (AuthenticatedUser) authentication.getPrincipal();
        List<ProjectDetailsResponse> allProjectsDetails = projectService.getProjectsDetails(validUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "success", true,
                "projects", allProjectsDetails));
    }

    // get all projects
    @GetMapping("/")
    public ResponseEntity<?> findAllCompanyProjects(Authentication authentication) {
        AuthenticatedUser validUser = (AuthenticatedUser) authentication.getPrincipal();// get data from context
        List<Project> projects = projectService.viewAllCompanyProjects(validUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "success", true,
                "projects", projects,
                "message", "Successfully fetched company projects"));
    }

    // get all projects
    @GetMapping("/my")
    public ResponseEntity<?> findAllMyProjects(Authentication authentication) {
        AuthenticatedUser validUser = (AuthenticatedUser) authentication.getPrincipal();// get data from context
        List<Project> projects = projectService.viewAllProjectsCreatedByMe(validUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "success", true,
                "projects", projects,
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
