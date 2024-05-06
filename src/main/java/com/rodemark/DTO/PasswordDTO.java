package com.rodemark.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
