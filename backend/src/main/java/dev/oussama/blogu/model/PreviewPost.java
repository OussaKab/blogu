package dev.oussama.blogu.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PreviewPost implements Serializable {
    private Long id;
    private String title;
    private Date createdAt;
    private byte[] thumbnail;
    private String mimeType;
    private String createdBy;
}
