package ru.mirea.cofi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.cofi.entitys.User;
import ru.mirea.cofi.security.dto.UserDto;
import ru.mirea.cofi.security.exeption.ConflictException;
import ru.mirea.cofi.security.payload.UserDtoPayload;
import ru.mirea.cofi.services.UserService;

/**
 * The type User signup controller.
 */
@Controller
@RequestMapping("/signup")
public class UserSignupController {
    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * Signup new user response entity.
     *
     * @param userDtoPayload the user dto payload
     * @return the response entity
     */
    @PostMapping("/user")
    public ResponseEntity<UserDto> signupNewUser(@RequestBody UserDtoPayload userDtoPayload) {
        if (userService.findByEmail(userDtoPayload.getEmail()).isPresent())
            throw new ConflictException();

        User registeredUser = userService.registerNewUser(userDtoPayload, "ROLE_USER");

        return ResponseEntity.ok(new UserDto(registeredUser));
    }

    /**
     * Signup new manager response entity.
     *
     * @param userDtoPayload the user dto payload
     * @return the response entity
     */
    @PostMapping("/manager")
    public ResponseEntity<UserDto> signupNewManager(@RequestBody UserDtoPayload userDtoPayload) {
        if (userService.findByEmail(userDtoPayload.getEmail()).isPresent())
            throw new ConflictException();

        User registeredUser = userService.registerNewUser(userDtoPayload, "ROLE_MANAGER");
        return ResponseEntity.ok(new UserDto(registeredUser));
    }

}
