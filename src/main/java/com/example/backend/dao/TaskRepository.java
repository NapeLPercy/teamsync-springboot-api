package com.example.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.backend.model.Task;
import com.example.backend.model.TaskStatusType;

@Repository
public class TaskRepository {
    private final JdbcClient jdbcClient;

    public TaskRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public int saveTask(Task task) {
        System.out.println("THIS IS TASK DATA ==="+task.toString());

        return jdbcClient
                .sql("INSERT INTO task(id,title,description,status, priority, due_date, user_id, project_id) " +
                        "VALUES(?,?,?,?::task_statuses,?::task_priorities,?::date,?,?)")
                .params(List.of(task.getId(), task.getTitle(), task.getDescription(), task.getStatus().name(),
                        task.getPriority().name(), task.getDueDate(), task.getUserId(), task.getProjectId()))
                .update();
    }

    public Optional<Task> findById() {
        return null;
    }

    public List<Task> findAll(String id) {
        return jdbcClient.sql("SELECT * FROM tasks WHERE user_id =:user_id")
                .param("user_id", id)
                .query(Task.class)
                .list();
    }

    public String delete(String id) {
        return null;
    }

    public String update(String taskId, Task task) {
        int update = jdbcClient
                .sql("UPDATE tasks SET status=?::task_statuses WHERE id=?")
                .params(List.of(task.getStatus().name(), taskId))
                .update();
        return update == 1 ? "Successful" : "Not successful";
    }

   
}
