package dev.oussama.blogu.models;

import lombok.Data;

@Data
public class PostView extends UploadPost{
    private Long id;
    private String username;
}
