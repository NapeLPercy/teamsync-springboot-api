package com.example.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dao.CommentRepository;
import com.example.backend.dto.CommentsResponse;
import com.example.backend.model.AuthenticatedUser;
import com.example.backend.model.Comment;
import com.example.backend.service.CommentService;

import java.util.*;

@RequestMapping("api/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllComments(@AuthenticationPrincipal AuthenticatedUser validUser,
            @PathVariable("id") String taskId) {

        List<CommentsResponse> comments = commentService.getAllComments(validUser, taskId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "comments successfully fetched",
                "success", true,
                "comments", comments));
    }

    @PostMapping("/")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal AuthenticatedUser validUser,
            @RequestBody Comment comment) {

        String commentId = commentService.addComment(validUser, comment);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                "message", "comments successfully created",
                "success", true,
                "commentId", commentId));
    }

    /*
     * @GetMapping("/{id}")
     * public Optional<Comment> findById(@PathVariable String taskId) {
     * return commentRepository.findById(taskId);
     * }
     * 
     * @PutMapping("/{id}/content")
     * public String update(@PathVariable("id") String commentId, @RequestBody
     * Comment newCommentContent) {
     * return commentRepository.update(commentId, newCommentContent);
     * }
     */
}