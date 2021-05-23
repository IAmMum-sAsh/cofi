package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The type Item.
 */
@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {
    /**
     * The Title.
     */
    protected String title;
    /**
     * The Cost.
     */
    protected int cost;
    /**
     * The Description.
     */
    @Column(columnDefinition = "TEXT")
    protected String description;
}
