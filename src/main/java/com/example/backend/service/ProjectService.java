package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.backend.dao.ProjectDetailsResponse;
import com.example.backend.dao.ProjectRepository;
import com.example.backend.dao.UserRepository;
import com.example.backend.dto.ProjectRequest;
import com.example.backend.dto.UserResponse;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.AuthenticatedUser;
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
            throw new ResourceNotFoundException(errorMessage);
        }
        return companyId.get();
    }

    // add project
    public String createProject(ProjectRequest projectReq, AuthenticatedUser validUser) {

        if (!validUser.getRole().equals("ADMIN"))
            throw new UnauthorizedAccessException("You are not allowed to add projects");

        // checks if company exist
        String adminId = validUser.getUserId();
        String companyId = this.getCompanyId(adminId, "Company not found");

        String projectId = UUID.randomUUID().toString();
        Project project = new Project(
                projectId,
                projectReq.name(),
                projectReq.description(),
                projectReq.category(),
                projectReq.dueDate());

        project.setUserId(adminId);// creator id
        project.setCompanyId(companyId);

        projectRepository.insertProject(project);

        return projectId;
    }

    // view all projects
    public List<Project> viewAllCompanyProjects(AuthenticatedUser validUser) {
        if (!validUser.getRole().equals(("ADMIN"))) {
            throw new UnauthorizedAccessException("Only admin can view all company projects");
        }
        String adminId = validUser.getUserId();
        String companyId = this.getCompanyId(adminId, "Company not found");
        return projectRepository.fetchAllProjects(companyId);
    };

    // view all projects created by me
    public List<Project> viewAllProjectsCreatedByMe(AuthenticatedUser validUser) {
        if (!validUser.getRole().equals(("ADMIN"))) {
            throw new UnauthorizedAccessException("Only admin can view all company ");
        }
        String adminId = validUser.getUserId();
        getCompanyId(adminId, "Company not found");
        return projectRepository.fetchAllProjectsCreatedByMe(adminId);
    };

    // get all projects details
    public List<ProjectDetailsResponse> getProjectsDetails(AuthenticatedUser validUser) {
        String role = validUser.getRole();
        String userId = validUser.getUserId();

        if (!role.equals("ADMIN"))
            throw new UnauthorizedAccessException("Only admin can get project details");

        String companyId = this.getCompanyId(userId, "Company not found");

        return projectRepository.fetchProjectsDetails(companyId);
    }

    // delete project
    public void deleteProject(AuthenticatedUser validUser, String projectId) {

        if (!validUser.getRole().equals(("ADMIN"))) {
            throw new UnauthorizedAccessException("Only admin is allowed to delete company project");
        }

        String userId = validUser.getUserId();
        // ensure belonsg to a company
        String companyId = this.getCompanyId(userId, "You are not allowed to delete projects for this company");

        // ensure user and project belongs to company
        boolean projectBelongsToCompany = projectRepository.projectBelongsToCompany(projectId, companyId);
        if (!projectBelongsToCompany)
            throw new ResourceNotFoundException(
                    "Project does not belong to this company");

        projectRepository.deleteProject(projectId);
    }
}
