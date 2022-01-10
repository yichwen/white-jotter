package org.ursprung.wj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ursprung.wj.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    User getByUsernameAndPassword(String username, String password);
}
