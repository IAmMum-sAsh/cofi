package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.cofi.entitys.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
