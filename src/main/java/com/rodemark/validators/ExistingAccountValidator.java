package com.rodemark.validators;

import com.rodemark.models.UserAccount;
import com.rodemark.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ExistingAccountValidator implements Validator {
    private final UserService userService;
    @Autowired
    public ExistingAccountValidator(UserService userService){
        this.userService = userService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return UserAccount.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserAccount userAccount = (UserAccount) target;
        if (userService.findByLoginAndPassword(userAccount) != null) {
            errors.rejectValue("login", "", "Password or Login is incorrect!");
        }
    }
}
