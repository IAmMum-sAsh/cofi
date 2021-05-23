package ru.mirea.cofi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.cofi.entitys.Cafe;

/**
 * The interface Cafe repository.
 */
@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
