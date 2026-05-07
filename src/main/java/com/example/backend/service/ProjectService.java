package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.backend.dao.ProjectRepository;
import com.example.backend.dao.UserRepository;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.Project;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private String getCompanyId(String userId, String errorMessage) {
        Optional<String> companyId = userRepository.getCompanyId(userId);

        if (companyId.isEmpty()) {
            throw new UnauthorizedAccessException(errorMessage);
        }
        return companyId.get();
    }

    // add project
    public String createProject(Project project, String userId) {
        String companyId = this.getCompanyId(userId, "You are not allowed to create projects for this company");

        String projectId = UUID.randomUUID().toString();
        project.setId(projectId);
        project.setCompanyId(companyId);

        projectRepository.insertProject(project);

        return projectId;
    }

    // view all projects
    public List<Project> viewAllCompanyProjects(String userId, String role) {
        if (!role.equals(("ADMIN"))) {
            throw new UnauthorizedAccessException("You are not allowed to view all company projects");
        }

        String companyId = this.getCompanyId(userId, "You are not allowed to view projects for this company");

        return projectRepository.findAll(companyId);
    };

    // delete projects
    public String deleteProject(String userId, String role, String projectId) {

        if (!role.equals(("ADMIN"))) {
            throw new UnauthorizedAccessException("You are not allowed to delete any company projects");
        }

        String companyId = this.getCompanyId(userId, "You are not allowed to delete projects for this company");

        projectRepository.delete(projectId, companyId);
        return projectId;
    }
}
