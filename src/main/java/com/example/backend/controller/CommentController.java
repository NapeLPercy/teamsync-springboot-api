package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dao.CommentRepository;
import com.example.backend.model.Comment;

import java.util.*;

@RequestMapping("api/comments")
@RestController
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

   /* @PostMapping
    public String create(@RequestBody Comment comment){
        return commentRepository.create(comment);
    }

    @GetMapping
    public List<Comment> findAll(@PathVariable String ownerId) {
        return commentRepository.findAll(ownerId);
    }

    @GetMapping("/{id}")
    public Optional<Comment> findById(@PathVariable String taskId) {
        return commentRepository.findById(taskId);
    }

    @PutMapping("/{id}/content")
    public String update(@PathVariable("id") String commentId, @RequestBody Comment newCommentContent) {
        return commentRepository.update(commentId, newCommentContent);
    }*/
}