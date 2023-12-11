package ru.effectivemobile.taskmanagementsystem.repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.taskmanagementsystem.entities.User;

import java.util.List;
import java.util.Optional;


@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findById(int id);

    @Query("select u from User u where u.email in (:performers)")
    List<User> findAllByEmailIn(@Param("performers") List<String> performers);
}
