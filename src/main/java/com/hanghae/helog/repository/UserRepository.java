package com.hanghae.helog.repository;

import com.hanghae.helog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(String id);
    Optional<User> findById(String id);
}
