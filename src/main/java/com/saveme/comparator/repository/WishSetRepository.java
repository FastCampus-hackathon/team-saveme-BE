package com.saveme.comparator.repository;

import com.saveme.comparator.domain.WishSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishSetRepository extends JpaRepository<WishSet, Long> {
}
