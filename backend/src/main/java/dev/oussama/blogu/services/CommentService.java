package dev.oussama.blogu.services;

import dev.oussama.blogu.models.Comment;
import dev.oussama.blogu.models.CommentDTO;
import dev.oussama.blogu.models.Post;
import dev.oussama.blogu.repository.CommentRepository;
import dev.oussama.blogu.repository.PostRepository;
import dev.oussama.blogu.repository.UserRepository;
import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentDTO create(CommentDTO c) throws PostNotFoundException {
        Comment comment = new Comment();
        comment.setText(c.getText());

        Post post = postRepository.findById(c.getPostId()).orElseThrow(() -> new PostNotFoundException(c.getPostId()));

        final String username = c.getUsername();

        dev.oussama.blogu.models.User fetchedUser = userRepository.findByCredentials_Username(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        comment.setCreatedBy(username);
        comment.setCreatedDate(new Date());
        comment.setUser(fetchedUser);
        comment.setPost(post);
        commentRepository.save(comment);
        return c;
    }

    public List<CommentDTO> getAllForPost(Long postId) {
        return commentRepository.findAllByPost_Id(postId).stream().map(c -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setUsername(c.getUser().getUsername());
            commentDTO.setText(c.getText());
            commentDTO.setCreatedAt(c.getCreatedDate());
            return commentDTO;
        }).collect(Collectors.toList());
    }
}
