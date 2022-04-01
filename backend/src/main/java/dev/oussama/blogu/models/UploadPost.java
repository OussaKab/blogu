package dev.oussama.blogu.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadPost implements Serializable {
    private String title;
    private String description;
}
