package dev.oussama.blogu.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Profile implements Serializable {
    private String username;
    private Date createdAt;
    private List<PreviewPost> posts;
    private long totalViews;
}
