package dev.oussama.blogu.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.oussama.blogu.model.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @NotNull
    private Date birthDate;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotNull
    private List<Role> roles;
}
