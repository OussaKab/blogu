package dev.oussama.blogu.models;

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
