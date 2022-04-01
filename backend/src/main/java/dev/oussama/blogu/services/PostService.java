package dev.oussama.blogu.services;

import dev.oussama.blogu.models.*;
import dev.oussama.blogu.repository.PostRepository;
import dev.oussama.blogu.repository.UserRepository;
import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post getPost(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        post.setViews(post.getViews() + 1);
        return postRepository.save(post);
    }

    public Long uploadPost(UploadPost uploadPost) {
        Post post = new Post();
        post.setTitle(uploadPost.getTitle());
        post.setDescription(uploadPost.getDescription());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByCredentials_Username(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername()));
        post.setUser(user);
        post.setCreatedBy(user.getUsername());
        post.setCreatedDate(new Date());

        post = postRepository.save(post);
        return post.getId();
    }

    public List<PreviewPost> getAllPosts() {
        return postRepository.findAll().stream().filter(p -> !p.isBlocked()).map(p -> {
            PreviewPost previewPost = new PreviewPost();
            previewPost.setId(p.getId());
            previewPost.setTitle(p.getTitle());
            previewPost.setCreatedBy(p.getUser().getUsername());
            previewPost.setCreatedAt(p.getCreatedDate());
            return previewPost;
        }).collect(Collectors.toList());
    }

    public Post editPost(PostView postView) throws PostNotFoundException {
        Post post = postRepository.findById(postView.getId()).orElseThrow(() -> new PostNotFoundException(postView.getId()));
        post.setTitle(postView.getTitle());
        post.setDescription(postView.getDescription());
        post.setLastModifiedBy(postView.getUsername());
        return postRepository.save(post);
    }

    public List<Post> searchForPosts(String search) {
        return postRepository.findAllByTitleOrDescriptionContaining(search, search);
    }

    public boolean moderatePost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        post.setBlocked(!post.isBlocked());
        return postRepository.save(post).isBlocked();
    }

    public List<PreviewPost> allBlocked() {
        return postRepository.findAll().stream().filter(Post::isBlocked).map(p -> {
            PreviewPost previewPost = new PreviewPost();
            previewPost.setId(p.getId());
            previewPost.setTitle(p.getTitle());
            previewPost.setCreatedBy(p.getUser().getUsername());
            previewPost.setCreatedAt(p.getCreatedDate());
            return previewPost;
        }).collect(Collectors.toList());
    }
}
