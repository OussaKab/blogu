package dev.oussama.blogu.models;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PreviewPost implements Serializable {
    private Long id;
    private String title;
    private Date createdAt;
    private String createdBy;
}
