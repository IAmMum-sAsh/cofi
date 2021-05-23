package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.cofi.entitys.Basket;
import ru.mirea.cofi.entitys.User;

import java.util.Optional;

/**
 * The interface Basket repository.
 */
@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    /**
     * Find by user optional.
     *
     * @param user the user
     * @return the optional
     */
    Optional<Basket> findByUser(User user);
}
