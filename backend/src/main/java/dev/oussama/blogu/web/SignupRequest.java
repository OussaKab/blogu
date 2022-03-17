package dev.oussama.blogu.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest {

    @NotBlank(message = "field email is required")
    @Email
    private String email;

    @NotNull
    private Credentials credentials;

    @NotNull
    private RoleAssign role;

    public String getSecret() {
        return this.credentials.getPassword();
    }

    /**
     * named getLogin to be compatible with the Credentials class
     */
    public String getLogin() {
        return this.credentials.getUsername();
    }
}