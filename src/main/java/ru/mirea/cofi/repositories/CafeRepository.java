package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.cofi.entitys.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
