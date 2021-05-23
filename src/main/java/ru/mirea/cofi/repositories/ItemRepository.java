package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.cofi.entitys.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
