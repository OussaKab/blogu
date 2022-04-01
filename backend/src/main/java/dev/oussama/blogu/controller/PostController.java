package dev.oussama.blogu.controller;

import dev.oussama.blogu.models.Post;
import dev.oussama.blogu.models.PostView;
import dev.oussama.blogu.models.PreviewPost;
import dev.oussama.blogu.models.UploadPost;
import dev.oussama.blogu.services.PostService;
import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PreviewPost> getAllPosts() {
        return postService.getAllPosts();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) throws PostNotFoundException {
        return postService.getPost(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Long uploadPost(@RequestBody UploadPost uploadPost) {
        return postService.uploadPost(uploadPost);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/edit")
    public Post editPost(@RequestBody PostView postView) throws PostNotFoundException {
        return this.postService.editPost(postView);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search/{search}")
    public List<Post> searchForPosts(@PathVariable String search){
        return postService.searchForPosts(search);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderate/{postId}")
    public boolean moderatePost(@PathVariable Long postId) throws PostNotFoundException {
    	return postService.moderatePost(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/moderated")
    public List<PreviewPost> moderatedContent() {
        return postService.allBlocked();
    }

}
