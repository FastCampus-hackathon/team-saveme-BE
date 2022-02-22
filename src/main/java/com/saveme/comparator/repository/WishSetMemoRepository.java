package com.saveme.comparator.repository;

import com.saveme.comparator.domain.WishSetMemo;
import com.saveme.comparator.domain.WishSetMemoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishSetMemoRepository extends JpaRepository<WishSetMemo, WishSetMemoPK> {

}
