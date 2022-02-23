package com.saveme.comparator.repository;

import com.saveme.comparator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long id);
    Boolean existsByEmail(String email);
    User findByEmail(String email);
}
