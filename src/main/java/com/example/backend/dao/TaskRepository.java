package com.example.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.backend.dto.TaskResponse;
import com.example.backend.model.Task;
import com.example.backend.model.TaskStatusType;

@Repository
public class TaskRepository {
    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public int saveTask(Task task) {
        return jdbcClient
                .sql("INSERT INTO task(id,title,description,status, priority, due_date,assigned_to, assigned_by, project_id) "
                        +
                        "VALUES(?,?,?,?::task_statuses,?::task_priorities,?::date,?,?,?)")
                .params(List.of(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().name(),
                        task.getPriority().name(), task.getDueDate(), task.getAssignedTo(), task.getAssignedBy(),
                        task.getProjectId()))
                .update();
    }

    public List<Task> findAll(String id) {
        return jdbcClient.sql("SELECT * FROM tasks WHERE user_id =:user_id")
                .param("user_id", id)
                .query(Task.class)
                .list();
    }

    public int delete(String taskId) {
        return jdbcClient.sql("DELETE FROM task WHERE id = :taskId")
                .param("taskId", taskId)
                .update();
    }

    public String update(String taskId, Task task) {
        int update = jdbcClient
                .sql("UPDATE tasks SET status=?::task_statuses WHERE id=?")
                .params(List.of(task.getStatus().name(), taskId))
                .update();
        return update == 1 ? "Successful" : "Not successful";
    }

    public List<TaskResponse> getAllTasks(String companyId) {
        return jdbcClient
                .sql("""
                        SELECT
                            t.id,
                            t.title,
                            t.description,
                            t.status::text AS status,
                            t.priority::text AS priority,
                            t.due_date AS dueDate,
                            t.created_at AS createdAt
                        FROM task t
                        INNER JOIN project p
                            ON p.id = t.project_id
                        WHERE p.company_id = :company_id
                        """)
                .param("company_id", companyId)
                .query(TaskResponse.class)
                .list();
    }

    public List<TaskResponse> getAllTasksByMe(String userId) {
        return jdbcClient
                .sql("""
                        SELECT
                            t.id,
                            t.title,
                            t.description,
                            t.status::text AS status,
                            t.priority::text AS priority,
                            t.due_date AS dueDate,
                            t.created_at AS createdAt
                        FROM task t
                        INNER JOIN project p
                            ON p.id = t.project_id
                        WHERE p.assigned_by = :assigned_by
                        """)
                .param("assigned_by", userId)
                .query(TaskResponse.class)
                .list();
    }

    public List<TaskResponse> getAllTasksForMe(String employeeId) {
        return jdbcClient
                .sql("""
                        SELECT
                            t.id,
                            t.title,
                            t.description,
                            t.status::text AS status,
                            t.priority::text AS priority,
                            t.due_date AS dueDate,
                            t.created_at AS createdAt
                        FROM task t
                        WHERE t.assigned_to = :assigned_to
                        """)
                .param("assigned_to", employeeId)
                .query(TaskResponse.class)
                .list();
    }

    public Optional<String> getProjectId(String taskId) {
        return jdbcClient
                .sql("SELECT project_id FROM task WHERE id =:task_id")
                .param("task_id", taskId)
                .query(String.class)
                .optional();
    }

    // public boolean taskBelongToCompanyProject(
    // String taskId,
    // String companyId) {

    // return jdbcClient
    // .sql("""
    // SELECT EXISTS (
    // SELECT 1
    // FROM task
    // WHERE id = :_id
    // AND company_id = :company_id

    // )
    // """)
    // .param("user_id", userId)
    // .param("company_id", companyId)
    // .query(Boolean.class)
    // .single();
    // }

}
