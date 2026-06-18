package org.example.hospitalapi.repository;

import java.util.Optional;
import org.example.hospitalapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}
