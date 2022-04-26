package dev.oussama.blogu.controller;

import dev.oussama.blogu.model.CommentDTO;
import dev.oussama.blogu.services.CommentService;
import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/post/{postId}")
    public List<CommentDTO> getAllForPost(@PathVariable Long postId){
        return commentService.getAllForPost(postId);
    }

    @PostMapping
    public CommentDTO create(@RequestBody CommentDTO comment) throws PostNotFoundException {
        return commentService.create(comment);
    }
}
