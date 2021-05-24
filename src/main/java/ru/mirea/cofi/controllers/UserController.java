package ru.mirea.cofi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.cofi.entitys.User;
import ru.mirea.cofi.security.dto.UserDto;
import ru.mirea.cofi.security.exeption.NotFoundException;
import ru.mirea.cofi.services.UserService;

/**
 * The type User controller.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Gets user.
     *
     * @param userId the user id
     * @return the user
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User user = userService.findById(userId).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(new UserDto(user));
    }
}
