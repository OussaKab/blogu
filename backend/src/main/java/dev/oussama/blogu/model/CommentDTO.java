package dev.oussama.blogu.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentDTO implements Serializable{
    private String text;
    private Long postId;
    private String username;
    private Date createdAt;
}
