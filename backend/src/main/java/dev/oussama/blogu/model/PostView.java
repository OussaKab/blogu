package dev.oussama.blogu.model;

import lombok.Data;

@Data
public class PostView extends UploadPost{
    private Long id;
    private String username;
}
