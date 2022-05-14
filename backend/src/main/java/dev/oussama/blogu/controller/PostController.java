package dev.oussama.blogu.controller;

import dev.oussama.blogu.model.ModerationDTO;
import dev.oussama.blogu.model.Post;
import dev.oussama.blogu.model.PostView;
import dev.oussama.blogu.model.PreviewPost;
import dev.oussama.blogu.services.PostService;
import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
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
    public Long uploadPost(@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("contentType") String contentType,
                           @RequestParam("content") MultipartFile content,
                           @RequestParam("thumbnail") MultipartFile thumbnail) {
        return postService.uploadPost(title, description, contentType, content, thumbnail);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/edit")
    public Post editPost(@RequestBody PostView postView) throws PostNotFoundException {
        return this.postService.editPost(postView);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search/{search}")
    public List<PreviewPost> searchForPosts(@PathVariable String search) {
        return postService.searchForPosts(search);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderate")
    public boolean moderatePost(@NotNull @RequestBody ModerationDTO moderation) throws PostNotFoundException {
        return postService.moderatePost(moderation);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/unmoderate/{postId}")
    public boolean moderatePost(@PathVariable Long postId) throws PostNotFoundException {
        return postService.unmoderatePost(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping("/moderations-for-user/{username}")
    public List<PreviewPost> moderationsOfUser(@PathVariable String username) {
        return postService.allBlockedForModerator(username);
    }
}
