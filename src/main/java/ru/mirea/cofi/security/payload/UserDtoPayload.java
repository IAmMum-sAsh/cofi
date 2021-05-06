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

    /**
     * Instantiates a new User dto payload.
     *
     * @param email               the email
     * @param password            the password
     */
    public UserDtoPayload(String email, String password) {

        this.email = email;
        this.password = password;
    }
}
