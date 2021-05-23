package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * The type Basket.
 */
@Entity
@Table(name = "baskets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket extends BaseEntity{
    /**
     * The User.
     */
    @OneToOne
    protected User user;
    /**
     * The Items.
     */
    @ManyToMany
    protected List<Item> items;
}
