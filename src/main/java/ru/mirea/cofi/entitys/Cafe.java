package ru.mirea.cofi.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cafe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cafe extends BaseEntity{
    protected String adress;
}
