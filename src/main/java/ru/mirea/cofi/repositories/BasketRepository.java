package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.cofi.entitys.Basket;
import ru.mirea.cofi.entitys.User;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUser(User user);
}
