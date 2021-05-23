package ru.mirea.cofi.entitys;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The type Base entity.
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {
    /**
     * The Id.
     */
    @Id
    @GeneratedValue(generator = "increment")
    protected long id;
}
