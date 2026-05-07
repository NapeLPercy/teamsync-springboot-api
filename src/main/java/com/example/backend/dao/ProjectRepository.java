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

    public int insertProject(Project project) {
        return jdbcClient
                .sql("INSERT INTO project(id, name, description, user_id, company_id) VALUES(?,?,?,?,?)")
                .params(List.of(project.getId(), project.getName(), project.getDescription(), project.getUserId(),
                        project.getCompanyId()))
                .update();
    }

    public List<Project> findAll(String companyId) {
        return jdbcClient.sql("SELECT * FROM project WHERE company_id =:company_id")
                .param("company_id", companyId)
                .query(Project.class)
                .list();
    }

    public int delete(String projectId, String companyId) {
        return jdbcClient
                .sql("DELETE FROM project WHERE id = :project_id AND company_id = :company_id")
                .param("project_id", projectId)
                .param("company_id", companyId)
                .update();
    }

    /*
     * public Optional<Project> findById(String id) {
     * return jdbcClient
     * .sql("SELECT * FROM projects WHERE id=:id")
     * .param("id", id)
     * .query(Project.class).optional();
     * }
     * 
     * 
     */
}
