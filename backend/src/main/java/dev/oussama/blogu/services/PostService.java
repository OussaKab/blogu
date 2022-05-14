package dev.oussama.blogu.services;

import dev.oussama.blogu.config.ArtSoukUtils;
import dev.oussama.blogu.model.*;
import dev.oussama.blogu.repository.PostRepository;
import dev.oussama.blogu.repository.UserRepository;
import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        assert filename != null;
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
        return postRepository.findAllByModeration_BlockedFalse()
                .stream()
                .map(ArtSoukUtils::toPreviewPost)
                .collect(Collectors.toList());
    }

    public List<PreviewPost> searchForPosts(String search) {
        return postRepository
                .findAllByTitleOrDescriptionContaining(search, search)
                .stream()
                .map(ArtSoukUtils::toPreviewPost)
                .collect(Collectors.toList());
    }

    public boolean moderatePost(ModerationDTO moderationDTO) throws PostNotFoundException {
        Long postId = moderationDTO.getPostId();
        final String username = moderationDTO.getUsername();
        final String reason = moderationDTO.getReason();

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        Moderation moderation = new Moderation();
        moderation.setModerator(userRepository.findByCredentials_Username(username).orElseThrow());
        moderation.setBlocked(true);
        moderation.setReason(reason);

        post.setModeration(moderation);

        postRepository.save(post);
        return moderation.isBlocked();
    }

    public boolean unmoderatePost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        post.setModeration(new Moderation(null, null, false));

        postRepository.save(post);
        return post.getModeration().isBlocked();
    }

    public List<PreviewPost> allBlockedForModerator(String username) {
        return postRepository.findAllByModeration_Moderator_Credentials_UsernameAndModeration_BlockedTrue(username)
                .stream()
                .map(ArtSoukUtils::toPreviewPost)
                .collect(Collectors.toList());
    }
}
