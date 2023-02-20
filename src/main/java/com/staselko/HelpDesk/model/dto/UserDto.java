package com.staselko.HelpDesk.model.dto;

import com.staselko.HelpDesk.model.enums.Role;
import com.staselko.HelpDesk.utils.annotations.EmailValid;
import com.staselko.HelpDesk.utils.annotations.PasswordValid;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "The first_name cannot be empty")
    private String first_name;

    @NotBlank(message = "The last_name cannot be empty")
    private String last_name;

    @NotNull(message = "The role cannot be empty")
    private Role role;

    @EmailValid
    private String email;

    @PasswordValid
    private String password;
}
