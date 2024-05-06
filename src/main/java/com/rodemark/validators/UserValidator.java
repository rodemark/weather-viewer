package com.rodemark.validators;

import com.rodemark.DTO.UserDTO;
import com.rodemark.models.User;
import com.rodemark.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (userService.findByEmail(userDTO.getEmail()) != null) {
            errors.rejectValue("email", "", "This email is already taken.");
        }
    }

    public void validatePassword(String password, String confirmPassword, Errors errors){
        if (!password.equals(confirmPassword)){
            errors.rejectValue("password", "", "Passwords are not the same.");
        }
    }

    public boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
