package com.rodemark.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String login;
    private String password;
    private String confirmPassword;
}
