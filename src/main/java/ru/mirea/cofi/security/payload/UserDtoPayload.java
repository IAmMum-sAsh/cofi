package ru.mirea.cofi.security.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User dto payload.
 */
@Data
@NoArgsConstructor
public class UserDtoPayload extends BasicPayload{
    private String email;
    private String password;
    private String role;

    /**
     * Instantiates a new User dto payload.
     *
     * @param email    the email
     * @param password the password
     * @param role     the role
     */
    public UserDtoPayload(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
