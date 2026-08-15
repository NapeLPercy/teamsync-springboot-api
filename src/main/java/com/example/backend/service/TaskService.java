package com.example.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.backend.dao.ProjectRepository;
import com.example.backend.dao.TaskRepository;
import com.example.backend.exception.UnauthorizedAccessException;
import com.example.backend.model.Task;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    private String getProjectId(String userId, String projectId) {
        Optional<String> savedProjectId = projectRepository.getProjectId(projectId, userId);

        if (savedProjectId.isEmpty()) {
            throw new UnauthorizedAccessException("You are not authourized to complete that action");
        }
        return savedProjectId.get();
    }

    public int addTask(Task task, String userId) {
        //check if the request is by someone who belongs to this company
//check if user id bellonsg to this company
//check if the projectId belongs to this company

        String projectId = this.getProjectId(task.getProjectId(), task.getUserId());

        // checks if this task belongs to project that belongs to this user's company
        return taskRepository.saveTask(task);
    }
}
