package dev.oussama.blogu.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadPost implements Serializable {
    private String title;
    private String description;
    private String contentType;
}
