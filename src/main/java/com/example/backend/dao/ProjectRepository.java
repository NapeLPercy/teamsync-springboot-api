package com.example.backend.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import com.example.backend.model.*;

import java.time.LocalDate;
import java.util.*;

@Repository
public class ProjectRepository {
        private final JdbcClient jdbcClient;

        public ProjectRepository(JdbcClient jdbcClient) {
                this.jdbcClient = jdbcClient;
        }

        // add project
        public int insertProject(Project project) {
                return jdbcClient
                                .sql("INSERT INTO project(id, name, description,category, due_date, assigned_by, company_id) VALUES(?,?,?,?,?,?,?)")
                                .params(List.of(project.getId(), project.getName(), project.getDescription(),
                                                project.getCategory(),
                                                project.getDueDate(), project.getUserId(),
                                                project.getCompanyId()))
                                .update();
        }

        // fetch all projects
        public List<Project> fetchAllProjects(String companyId) {
                return jdbcClient.sql(
                                "SELECT id,name,description,category, due_date, created_at, assigned_by AS userId FROM project WHERE company_id =:company_id")
                                .param("company_id", companyId)
                                .query(Project.class)
                                .list();
        }

        // fetch all projects i created
        public List<Project> fetchAllProjectsCreatedByMe(String adminId) {
                return jdbcClient.sql(
                                "SELECT id,name,description,category, due_date, created_at, assigned_by AS userId FROM project WHERE assigned_by =:assigned_by")
                                .param("assigned_by", adminId)
                                .query(Project.class)
                                .list();
        }

        // fetch project name and id
        public List<ProjectDetailsResponse> fetchProjectsDetails(String companyId) {
                return jdbcClient
                                .sql("SELECT p.id, p.name, p.due_date AS dueDate FROM project p WHERE company_id =:company_id")
                                .param("company_id", companyId)
                                .query(ProjectDetailsResponse.class)
                                .list();
        }

        // get project deadline
        public Optional<LocalDate> getProjectDueDate(String projectId) {
                return jdbcClient
                                .sql("SELECT due_date AS dueDate FROM project WHERE id = :project_id")
                                .param("project_id", projectId)
                                .query(LocalDate.class)
                                .optional();
        }

        public int delete(String projectId, String companyId) {
                return jdbcClient
                                .sql("DELETE FROM project WHERE id = :project_id AND company_id = :company_id")
                                .param("project_id", projectId)
                                .param("company_id", companyId)
                                .update();
        }

        // verify project belongs to a company
        public boolean projectBelongsToCompany(
                        String projectId,
                        String companyId) {

                return jdbcClient
                                .sql("""
                                                SELECT EXISTS (
                                                    SELECT 1
                                                    FROM project
                                                    WHERE id = :project_id
                                                    AND company_id = :company_id
                                                )
                                                """)
                                .param("project_id", projectId)
                                .param("company_id", companyId)
                                .query(Boolean.class)
                                .single();
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
