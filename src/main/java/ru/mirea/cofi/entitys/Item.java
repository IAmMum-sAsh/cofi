package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {
    protected String title;
    protected int cost;
    @Column(columnDefinition = "TEXT")
    protected String description;
}
