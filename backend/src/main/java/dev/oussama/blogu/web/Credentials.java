package dev.oussama.blogu.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Credentials implements Serializable {

    @NotBlank(message = "Login is required")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Secret is required")
    private String password;
}