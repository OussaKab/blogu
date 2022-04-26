package dev.oussama.blogu.services;

import dev.oussama.blogu.model.*;
import dev.oussama.blogu.repository.PostRepository;
import dev.oussama.blogu.repository.UserRepository;
import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final FileService fileService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(FileService fileService, PostRepository postRepository, UserRepository userRepository) {
        this.fileService = fileService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post getPost(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        post.setViews(post.getViews() + 1);
        return postRepository.save(post);
    }

    public Long uploadPost(String title, String description, String contentType, MultipartFile content, MultipartFile thumbnail) {
        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setContentType(ContentType.valueOf(contentType.toUpperCase()));
        String filename = thumbnail.getOriginalFilename();

        post.setThumbnailMimeType("image/"+ filename.substring(filename.lastIndexOf(".") + 1));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String username = userDetails.getUsername();

        post.setContentPath(fileService.saveFile(content, username));
        post.setThumbnailPath(fileService.saveFile(thumbnail, username));

        User user = userRepository.findByCredentials_Username(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername()));
        post.setUser(user);
        post.setCreatedBy(user.getUsername());
        post.setCreatedDate(new Date());

        post = postRepository.save(post);
        return post.getId();
    }

    public List<PreviewPost> getAllPosts() {
        return postRepository.findAll()
            .stream()
            .filter(p -> !p.isBlocked())
            .map(p -> {
                PreviewPost previewPost = new PreviewPost();
                previewPost.setId(p.getId());
                previewPost.setTitle(p.getTitle());
                previewPost.setCreatedBy(p.getUser().getUsername());
                previewPost.setCreatedAt(p.getCreatedDate());
                previewPost.setMimeType(p.getThumbnailMimeType());
                try {
                    previewPost.setThumbnail(Files.readAllBytes(Paths.get(p.getThumbnailPath())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return previewPost;
            }).collect(Collectors.toList());
    }

    public Post editPost(PostView postView) throws PostNotFoundException {
        Post post = postRepository.findById(postView.getId())
                .orElseThrow(() -> new PostNotFoundException(postView.getId()));
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
