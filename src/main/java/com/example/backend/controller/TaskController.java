package com.example.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dao.TaskRepository;
import com.example.backend.dto.TaskRequest;
import com.example.backend.dto.TaskResponse;
import com.example.backend.model.AuthenticatedUser;
import com.example.backend.model.Task;
import com.example.backend.service.TaskService;

@RequestMapping("api/tasks")
@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createTask(@AuthenticationPrincipal AuthenticatedUser authUser,
            @RequestBody TaskRequest taskReq) {

        String taskId = taskService.addTask(taskReq, authUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("success", true,
                        "taskId", taskId,
                        "message", "successfully added a task"));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllTasks(@AuthenticationPrincipal AuthenticatedUser validUser) {
        List<TaskResponse> tasks = taskService.getAllTasks(validUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "success", true,
                "message", "successfuly fetched all tasks",
                "tasks", tasks

        ));
    }

    @GetMapping("/my")
    public ResponseEntity<?> findAllTasksByMe(@AuthenticationPrincipal AuthenticatedUser validUser) {
        List<TaskResponse> tasks = taskService.getAllTasksByMe(validUser);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "Successfully fetched all tasks assigned by you",
                "success", true,
                "tasks", tasks));

    }

    /**
     * @PutMapping("/{id}/status")
     * public String update(@PathVariable("id") String taskId, @RequestBody Task
     * task) {
     * return taskRepository.update(taskId, task);
     * }
     */

}
