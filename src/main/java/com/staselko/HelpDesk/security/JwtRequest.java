package com.staselko.HelpDesk.security;

import com.staselko.HelpDesk.utils.annotations.EmailValid;
import com.staselko.HelpDesk.utils.annotations.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtRequest implements Serializable {
    @EmailValid
    private String username;

    @PasswordValid
    private String password;

}
