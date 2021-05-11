package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.cofi.entitys.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
