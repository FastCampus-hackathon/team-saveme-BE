package com.saveme.comparator.repository;

import com.saveme.comparator.domain.Recruition;
import com.saveme.comparator.domain.User;
import com.saveme.comparator.domain.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish,Long> {

    boolean existsByUserAndRecruition_RecruitmentId(User user, Long recruitmentId);
    void deleteByUserAndRecruition_RecruitmentId(User user, Long recruitmentId);
}
