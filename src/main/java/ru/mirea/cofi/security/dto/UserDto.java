package ru.mirea.cofi.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.cofi.entitys.User;

/**
 * The type User dto.
 */
@Data
@NoArgsConstructor
public class UserDto {
    private long id;
    private String email;
    private String role;

    /**
     * Instantiates a new User dto.
     *
     * @param user the user
     */
    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
