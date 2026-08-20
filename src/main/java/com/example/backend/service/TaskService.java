package com.example.backend.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import com.example.backend.dao.ProjectRepository;
import com.example.backend.dao.TaskRepository;
import com.example.backend.dao.UserRepository;
import com.example.backend.dto.TaskRequest;
import com.example.backend.dto.TaskResponse;
import com.example.backend.exception.InvalidResourceException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.AuthenticatedUser;
import com.example.backend.model.Task;
import com.example.backend.model.TaskStatusType;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // Get the company that the authenticated user belongs to.
    private String getUserCompanyId(String userId, String errorMessage) {
        Optional<String> companyId = userRepository.getCompanyId(userId);

        if (companyId.isEmpty())
            throw new ResourceNotFoundException(errorMessage);

        return companyId.get();
    }

    /*
     * ADD TASK
     */
    public String addTask(TaskRequest taskReq, AuthenticatedUser validUser) {

        // Only admins are allowed to create tasks.
        if (!validUser.getRole().equals("ADMIN"))
            throw new UnauthorizedAccessException("Only admin can add a task");

        String adminId = validUser.getUserId();

        // Determine the company from the authenticated admin.
        // The company is never trusted from the request.
        String companyId = this.getUserCompanyId(
                adminId,
                "User does not belong to a company");

        // Ensure the selected project belongs to the admin's company.
        boolean projectBelongsToCompany = projectRepository.projectBelongsToCompany(
                taskReq.projectId(),
                companyId);

        if (!projectBelongsToCompany)
            throw new ResourceNotFoundException(
                    "Project does not belong to this company");

        // Ensure the employee being assigned to the task belongs
        // to the same company as the project.
        boolean userBelongsToCompany = userRepository.userBelongsToCompany(
                taskReq.assignedTo(),
                companyId);

        if (!userBelongsToCompany)
            throw new ResourceNotFoundException(
                    "User does not belong to this company");

        String taskId = UUID.randomUUID().toString();

        // VALID TASK DATE
        LocalDate projectDueDate = projectRepository
                .getProjectDueDate(taskReq.projectId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project due date not found"));

        if (taskReq.dueDate().isAfter(projectDueDate)) {
            throw new InvalidResourceException("Task due date cannot come after project due date");
        }
        Task task = new Task(
                taskId,
                taskReq.title(),
                taskReq.description(),
                TaskStatusType.NEW,
                taskReq.priority(),
                taskReq.dueDate(),
                taskReq.assignedTo(),
                adminId,
                taskReq.projectId());

        // All company-level relationships have been validated.
        taskRepository.saveTask(task);

        return taskId;
    }

    // get all tasks
    public List<TaskResponse> getAllTasks(AuthenticatedUser validUser) {

        // Only admins are allowed to view tasks.
        if (!validUser.getRole().equals("ADMIN"))
            throw new UnauthorizedAccessException("Only admin can view a task");

        String adminId = validUser.getUserId();

        // Determine the company from the authenticated admin.
        // The company is never trusted from the request.
        String companyId = this.getUserCompanyId(
                adminId,
                "User does not belong to a company");

        return taskRepository.getAllTasks(companyId);

    }
}
