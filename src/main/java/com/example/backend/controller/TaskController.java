package com.example.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dao.TaskRepository;
import com.example.backend.model.Task;

@RequestMapping("api/tasks")
@RestController
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /*
     * @PostMapping
     * public String create(@RequestBody Task task) {
     * System.out.println(task.toString());
     * return taskRepository.create(task);
     * }
     * 
     * @GetMapping("/{id}")
     * public List<Task> findAll(@PathVariable String id) {
     * return taskRepository.findAll(id);
     * }
     * 
     * @PutMapping("/{id}/status")
     * public String update(@PathVariable("id") String taskId, @RequestBody Task
     * task) {
     * return taskRepository.update(taskId, task);
     * }
     */

}
