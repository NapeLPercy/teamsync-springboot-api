package com.example.backend.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.backend.dto.CommentsResponse;
import com.example.backend.model.Comment;

import java.util.*;

@Repository
public class CommentRepository {
    private final JdbcClient jdbcClient;

    public CommentRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public int addComment(Comment comment) {
        return jdbcClient
                .sql("INSERT INTO comment(id, content,submitted_by, task_id) VALUES(?,?,?,?)")
                .params(List.of(comment.getId(), comment.getContent(), comment.getSubmittedBy(), comment.getTaskId()))
                .update();
    }

    public List<CommentsResponse> findAllTaskComments(String taskId) {
        return jdbcClient
                .sql("""
                        SELECT
                            c.id,
                            c.content,
                            c.created_at AS createdAt,
                            c.task_id AS taskId,
                            u.full_name AS fullName,
                            r.user_role AS role
                        FROM comment c
                        INNER JOIN users u
                            ON u.id = c.submitted_by
                        INNER JOIN role r
                            ON r.user_id = u.id
                        WHERE c.task_id = :task_id
                        ORDER BY c.created_at ASC
                        """)
                .param("task_id", taskId)
                .query(CommentsResponse.class)
                .list();
    }

    public Optional<Comment> findById(String taskId) {
        return jdbcClient
                .sql("SELECT * FROM comments WHERE id=:id")
                .param("id", taskId)
                .query(Comment.class)
                .optional();
    }

    public String update(String commentId, Comment newCommentContent) {
        int updated = jdbcClient.sql("UPDATE comments SET content=? WHERE id=?")
                .params(List.of(newCommentContent, commentId))
                .update();
        return updated == 1 ? "Content updated successfully" : "Not updated";
    }
}