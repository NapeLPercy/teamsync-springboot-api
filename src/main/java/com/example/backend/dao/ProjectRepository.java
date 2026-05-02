package com.example.backend.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import com.example.backend.model.*;
import java.util.*;

@Repository
public class ProjectRepository {
    private final JdbcClient jdbcClient;

    public ProjectRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

   /* public String create(Project project) {
        int created = jdbcClient
                .sql("INSERT INTO projects(id, name, description, user_id) VALUES(?,?,?,?)")
                .params(List.of(project.getId(), project.getName(), project.getDescription(), project.getUserId()))
                .update();
        return created == 1 ? "Successfuly created" : "Not created";
    }

    public Optional<Project> findById(String id) {
        return jdbcClient
                .sql("SELECT * FROM projects WHERE id=:id")
                .param("id", id)
                .query(Project.class).optional();
    }

    public List<Project> findAll() {
        return jdbcClient.sql("SELECT * FROM projects")
                .query(Project.class).list();
    }

    public String delete(String id) {
        int deleted = jdbcClient
                .sql("DELETE FROM projects WHERE id =:id")
                .param("id", id)
                .update();
        return deleted == 1 ? "Project deleted" : "Project not deleted";
    }*/
}
