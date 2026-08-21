package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.backend.dao.CommentRepository;
import com.example.backend.dao.ProjectRepository;
import com.example.backend.dao.TaskRepository;
import com.example.backend.dao.UserRepository;
import com.example.backend.dto.CommentsResponse;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.AuthenticatedUser;
import com.example.backend.model.Comment;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
            ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    private void validateCommentAccess(AuthenticatedUser validUser) {
        // Only employees and admins can access comments
        if (!validUser.getRole().equals("EMPLOYEE") &&
                !validUser.getRole().equals("ADMIN")) {
            throw new UnauthorizedAccessException(
                    "Only admin or employee can access comments");
        }
    }

    public String addComment(AuthenticatedUser validUser, Comment comment) {
        validateCommentAccess(validUser);

        String userId = validUser.getUserId();
        // Use the authenticated user as the comment author
        comment.setSubmittedBy(userId);

        // Get the user's company
        Optional<String> companyId = userRepository.getCompanyId(userId);
        if (companyId.isEmpty()) {
            throw new ResourceNotFoundException("Company not found");
        }

        String taskId = comment.getTaskId();

        // Check that the task exists
        Optional<String> projectId = taskRepository.getProjectId(taskId);
        if (projectId.isEmpty()) {
            throw new ResourceNotFoundException("Task not found");
        }

        // Ensure the task's project belongs to the user's company
        boolean projectBelongsToCompany = projectRepository.projectBelongsToCompany(
                projectId.get(),
                companyId.get());

        if (!projectBelongsToCompany) {
            throw new ResourceNotFoundException("Project not found");
        }

        String commentId = UUID.randomUUID().toString();
        comment.setId(commentId);

        commentRepository.addComment(comment);

        return commentId;
    }

    public List<CommentsResponse> getAllComments(
            AuthenticatedUser validUser,
            String taskId) {

        validateCommentAccess(validUser);

        String userId = validUser.getUserId();

        // Get the user's company
        Optional<String> companyId = userRepository.getCompanyId(userId);
        if (companyId.isEmpty()) {
            throw new ResourceNotFoundException("Company not found");
        }

        // Check that the task exists
        Optional<String> projectId = taskRepository.getProjectId(taskId);
        if (projectId.isEmpty()) {
            throw new ResourceNotFoundException("Task not found");
        }

        // Ensure the task's project belongs to the user's company
        boolean projectBelongsToCompany = projectRepository.projectBelongsToCompany(
                projectId.get(),
                companyId.get());

        if (!projectBelongsToCompany) {
            throw new ResourceNotFoundException("Project not found");
        }

        return commentRepository.findAllTaskComments(taskId);
    }
}
