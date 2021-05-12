package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "baskets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket extends BaseEntity{
    @OneToOne
    protected User user;
    @ManyToMany
    protected List<Item> items;
}
