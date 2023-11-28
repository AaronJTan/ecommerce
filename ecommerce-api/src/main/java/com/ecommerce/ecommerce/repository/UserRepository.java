package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u.username FROM User u WHERE u.email = ?1")
    String findUsernameByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
