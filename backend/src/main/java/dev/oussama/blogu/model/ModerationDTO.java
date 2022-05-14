package dev.oussama.blogu.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ModerationDTO implements Serializable {
    @NotBlank
    private String username;
    @NotNull
    private Long postId;
    @NotBlank
    private String reason;
}
