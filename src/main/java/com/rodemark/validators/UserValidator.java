package com.rodemark.validators;

import com.rodemark.DTO.UserDTO;
import com.rodemark.models.UserAccount;
import com.rodemark.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return UserAccount.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (userService.findByLogin(userDTO.getLogin()) != null) {
            errors.rejectValue("login", "", "This login is already taken.");
        }
    }

    public void validatePassword(String password, String confirmPassword, Errors errors){
        if (!password.equals(confirmPassword)){
            errors.rejectValue("password", "", "Passwords are not the same.");
        }
    }
}
