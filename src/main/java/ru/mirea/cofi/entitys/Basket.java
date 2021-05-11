package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "baskets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket extends BaseEntity{
    protected long user_id;
    @Column(columnDefinition = "TEXT")
    protected String items;
}
