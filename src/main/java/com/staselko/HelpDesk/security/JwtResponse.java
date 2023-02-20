package com.staselko.HelpDesk.security;

import com.staselko.HelpDesk.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtResponse implements Serializable {
    private String token;
    private String userName;
    private String role;
}