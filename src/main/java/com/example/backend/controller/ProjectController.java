package com.example.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.example.backend.dao.ProjectRepository;
import com.example.backend.model.Project;
import java.util.*;

@RequestMapping("api/projects")
@RestController
public class ProjectController {
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

  /*  @PostMapping
    public String create(@RequestBody Project project){
        return projectRepository.create(project);
    }

    @GetMapping
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Project> findById(@PathVariable String id){
        return projectRepository.findById(id);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        return projectRepository.delete(id);
    }
*/
}
