package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The type User.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    /**
     * The Email.
     */
    protected String email;
    /**
     * The Password.
     */
    protected String password;
    /**
     * The Role.
     */
    protected String role;

}
