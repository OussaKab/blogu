package dev.oussama.blogu.services;

import dev.oussama.blogu.models.Post;
import dev.oussama.blogu.models.PreviewPost;
import dev.oussama.blogu.models.Profile;
import dev.oussama.blogu.repository.PostRepository;
import dev.oussama.blogu.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public ProfileService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Profile getProfile(String username) {
        Profile profile = userRepository.findByCredentials_Username(username).map(u -> {
            Profile p = new Profile();
            p.setUsername(u.getUsername());
            p.setCreatedAt(u.getCreatedDate());
            return p;
        }).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Post> posts = postRepository.findAllByUser_Credentials_Username(username);

        profile.setTotalViews(posts.parallelStream().mapToLong(Post::getViews).sum());

        profile.setPosts(posts.stream().filter(p -> !p.isBlocked()).map(p -> {
            PreviewPost previewPost = new PreviewPost();
            previewPost.setId(p.getId());
            previewPost.setTitle(p.getTitle());
            previewPost.setCreatedBy(username);
            previewPost.setCreatedAt(p.getCreatedDate());
            return previewPost;
        }).collect(Collectors.toList()));

        return profile;
    }

}
