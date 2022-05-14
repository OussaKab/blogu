package dev.oussama.blogu.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PreviewPost implements Serializable {
    private Long id;
    private String title;
    private String createdBy;
    private String mimeType;
    private Date createdAt;
    private byte[] thumbnail;
    private long views;
    private String moderationReason;
}