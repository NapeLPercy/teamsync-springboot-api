package com.example.backend.dao;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Comment;

import java.util.*;

@Repository
public class CommentRepository {
    private final JdbcClient jdbcClient;

    public CommentRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /*public String create(Comment comment) {
        int created = jdbcClient
                .sql("INSERT INTO comments(id, content, task_id) VALUES(?,?,?)")
                .params(List.of(comment.getId(), comment.getContent(), comment.getTaskId()))
                .update();
        return created == 1 ? "Successfuly create" : "Not created";
    }

    public List<Comment> findAll(String ownerId) {
        return jdbcClient.sql("SELECT * FROM comments")
                .query(Comment.class)
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
    }*/
}