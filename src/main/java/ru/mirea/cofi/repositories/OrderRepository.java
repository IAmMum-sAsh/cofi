package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.cofi.entitys.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
