package ru.effectivemobile.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.taskmanagementsystem.entities.Token;

@Transactional
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByUsername(String username);
}
