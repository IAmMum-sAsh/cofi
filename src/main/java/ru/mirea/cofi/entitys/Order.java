package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * The type Order.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{
    /**
     * The User.
     */
    @ManyToOne
    protected User user;
    /**
     * The Items.
     */
    @ManyToMany
    protected List<Item> items;
    /**
     * The Adress.
     */
    protected String adress;
    /**
     * The Status.
     */
    protected String status;
}
